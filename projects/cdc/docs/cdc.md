# CHANGE DATA CAPTURE
>## 1. About debezium
Debezium is a distributed platform that converts information from your existing databases into event streams, enabling applications to detect, and immediately respond to row-level changes in the databases.

Debezium is built on top of Apache Kafka and provides a set of Kafka Connect compatible connectors. Each of the connectors works with a specific database management system (DBMS). Connectors record the history of data changes in the DBMS by detecting changes as they occur, and streaming a record of each change event to a Kafka topic. Consuming applications can then read the resulting event records from the Kafka topic.
### **1.1. Debezium connector for mysql**

MySQL has a binary log (binlog) that records all operations in the order in which they are committed to the database. This includes changes to table schemas as well as changes to the data in tables. MySQL uses the binlog for replication and recovery.

The Debezium MySQL connector reads the binlog, produces change events for row-level INSERT, UPDATE, and DELETE operations, and emits the change events to Kafka topics. Client applications read those Kafka topics.

When a Debezium MySQL connector is first started, it performs an initial consistent snapshot of your database. 
If the connector fails, stops, or is rebalanced while performing the initial snapshot, then after the connector restarts, it performs a new snapshot. After that intial snapshot is completed, the Debezium MySQL connector restarts from the same position in the binlog so it does not miss any updates. 

However, in some situations the data that the connector obtained during the initial snapshot might become stale, lost, or incomplete. To provide a mechanism for recapturing table data, Debezium includes an option to perform ad-hoc snapshots

<u>Data type mappings</u>

[Insert table here]

<u>Setup mysql for enabling debezium captures binlog</u>

1. [Creating a user & grant appropriate permissions](https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-creating-user)
2. [Enabling the binlog](https://debezium.io/documentation/reference/stable/connectors/mysql.html#enable-mysql-binlog)
3. [Enabling GTIDs (if need)](https://debezium.io/documentation/reference/stable/connectors/mysql.html#enable-mysql-gtids)
4. [Configuring session timeouts](https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-session-timeouts)
5. [Enabling query log events](https://debezium.io/documentation/reference/stable/connectors/mysql.html#enable-query-log-events)

<u>Deployment</u> (see [Deployment section](#2-deployment))

### **1.2. Debezium connector for postgresql**
The Debezium PostgreSQL connector captures row-level changes in the schemas of a PostgreSQL database

The first time it connects to a PostgreSQL server or cluster, the connector takes a consistent snapshot of all schemas. After that snapshot is complete, the connector continuously captures row-level changes that insert, update, and delete database content and that were committed to a PostgreSQL database. The connector generates data change event records and streams them to Kafka topics. For each table, the default behavior is that the connector streams all generated events to a separate Kafka topic for that table.

In some situations the data that the connector obtained during the initial snapshot might become stale, lost, or incomplete. To provide a mechanism for recapturing table data, Debezium includes an option to perform ad hoc snapshots.

<u>Data type mappings</u>

[Insert table here]

<u>Setup postgresql for enabling debezium streams wal-log</u>

1. [Installing the logical decoding output plug-in](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#installing-postgresql-output-plugin): You can either install an output plug-in ([decoderbufs](https://github.com/debezium/postgres-decoderbufs) or [wal2json](https://github.com/eulerto/wal2json)) or using <i>pgoutput</i> - the postgresql built-in pgoutput
2. [Configuring the PostgreSQL server](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-server-configuration)
3. [Configuring PostgreSQL to allow replication with the Debezium connector host](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-host-replication-permissions)
4. Setting up permission: To provide a user with replication permissions, define a PostgreSQL role that has at least the REPLICATION and LOGIN permissions, and then grant that role to the user. If using <i>pgoutput</i>, [setting privileges to enable Debezium to create PostgreSQL publications](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-replication-user-privileges)

**Important**: [Setting up table-level replica identity](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-replica-identity) to control the amount of information that is available to the logical decoding plug-in for UPDATE and DELETE events

<u>Deployment</u> (see [Deployment section](#2-deployment))

>## 2. Deployment
### 2.1. Docker image
Our Debezium is installed as a plug-in of kafka-connect. Simply download one or more connector plug-in archives, extract their files into your Kafka Connect environment, and add the parent directory of the extracted plug-in(s) to Kafka Connect’s plugin path. See how we build the image [here](https://git.teko.vn/dataops/data-stacks/-/tree/master/docker/kafka)

### 2.1. Deploy debezium
Debezium is deployed on kubernetes using Strimzi operator. Refer [this](https://git.teko.vn/dataops/serving.cluster/-/tree/master/serving.platform/kafka) to see how we deploy debezium on kubernetes

### 2.3. Add a new connector
In order to add a new connector for capturing changes from a database, just add a new resource file in [here](https://git.teko.vn/dataops/datasources.next/-/tree/master/connector) then create a merge request for Data infra team.

<u>For mysql connectors</u>
```yaml
apiVersion: kafka.strimzi.io/v1beta2
kind: KafkaConnector
metadata:
  name: (1)
  labels:
    strimzi.io/cluster: debezium
spec:
  class: io.debezium.connector.mysql.MySqlConnector
  tasksMax: 1
  config:

    # Database connection info
    database.hostname: (2)
    database.port: (3)
    database.user: (4)
    database.password: (5)
    database.server.name: (6)
    database.include.list: (7)

    # Specifying tables to be captured
    table.include.list: >
      (8)

    # Config database history
    database.history.kafka.bootstrap.servers: (9)
    database.history.kafka.topic: (10)

    # Additional connector configurations. See https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-connector-properties for more information
    provide.transaction.metadata: true
    include.schema.changes: true
    database.history.store.only.captured.tables.ddl: true
    converters: boolean
    boolean.type: io.debezium.connector.mysql.converters.TinyIntOneToBooleanConverter
    topic.creation.default.replication.factor: "3"
    topic.creation.default.partitions: "12"
    topic.creation.default.retention.ms: "3888000000"
```
- (1). The connector's name. Naming convention: \<domain\>.\<database\>
- (2). Database hostname
- (3). Database port
- (4). Database user
- (5). Database password
- (6). Logical name that identifies and provides a namespace for the particular MySQL database server/cluster in which Debezium is capturing changes. Naming convention: debezium.\<domain\>.\<database\>. E.g. debezium.vnshop.cov
- (7). A comma-separated list of regular expressions that match the names of the databases for which to capture changes. By default, the connector captures changes in all databases.
- (8). A comma-separated list of regular expressions that match fully-qualified table identifiers of tables whose changes you want to capture. By default, the connector captures changes in every non-system table in each database whose changes are being captured. E.g. cov_service.order_invoices,cov_service.order_items,...
- (9). A list of host/port pairs that the connector uses for establishing an initial connection to the Kafka cluster. This connection is used for retrieving the database schema history previously stored by the connector, and for writing each DDL statement read from the source database.
- (10). The full name of the Kafka topic where the connector stores the database schema history.

<u> For postresql connectors</u>
```yaml
apiVersion: kafka.strimzi.io/v1beta2
kind: KafkaConnector
metadata:
  name: (1)
  labels:
    strimzi.io/cluster: debezium
spec:
  class: io.debezium.connector.postgresql.PostgresConnector
  tasksMax: 1
  config:

    # Database connection info
    database.hostname: (2)
    database.port: (3)
    database.user: (4)
    database.password: (5)
    database.server.name: (6)
    database.dbname: (7)
    plugin.name: pgoutput

    # Specifying tables to be captured
    schema.include.list: (8)
    table.include.list:
      (9)
    slot.name: (10)
    publication.name: (11)

    # Additional connector configurations. See https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-connector-properties for more information
    snapshot.mode: initial
    skipped.operations: none
    signal.data.collection: public.__debezium_signals
```
- (1). The connector's name
- (2). Database hostname
- (3). Database port
- (4). Database user
- (5). Database password
- (6). Logical name that identifies and provides a namespace for the particular MySQL database server/cluster in which Debezium is capturing changes. Naming convention: debezium.\<domain\>.\<database\>. E.g. debezium.vnshop.cov
- (7). The name of the PostgreSQL database from which to stream the changes
- (8). A comma-separated list of regular expressions that match names of schemas for which you want to capture changes. By default, all non-system schemas have their changes captured.
- (9). A comma-separated list of regular expressions that match fully-qualified table identifiers for tables whose changes you want to capture. By default, the connector captures changes in every non-system table in each schema whose changes are being captured. E.g. public.res_company,public.res_partner,...
- (10). The name of the PostgreSQL logical decoding slot that was created for streaming changes from a particular plug-in for a particular database/schema.
- (11). The name of the PostgreSQL publication created for streaming changes when using pgoutput.

**Note**: If using vault to save connector secrets (username/password for connection), add new environment variables into [env.sh](https://git.teko.vn/dataops/datasources.next/-/blob/master/connector/env.sh) file to get the secret from vault.

E.g.
```bash
...
export CONNECTOR_VNSHOP_WMS_USERNAME=$(vault kv get --field=username secret/datasources/vnshop/wms)
export CONNECTOR_VNSHOP_WMS_PASSWORD=$(vault kv get --field=password secret/datasources/vnshop/wms)
...
```
Then the connection username/password in the connector properties will use the environment variables
```yaml
apiVersion: kafka.strimzi.io/v1beta2
kind: KafkaConnector
metadata:
  name: (1)
  labels:
    strimzi.io/cluster: debezium
spec:
  class: io.debezium.connector.postgresql.PostgresConnector
  tasksMax: 1
  config:
    ...
    database.user:      "$CONNECTOR_VNSHOP_WMS_USERNAME"
    database.password:  "$CONNECTOR_VNSHOP_WMS_PASSWORD"
    ...
```

>## 3. Operating
### 3.2. Deploy a new connector (see [Deployment section](#2-deployment))
### 3.3. Trigger an ad-hoc snapshot
One of the biggest pain points in Debezium since its inception was the sup-optimal support for changes to the captured tables list. As a user, you create a new connector with a list of tables to be captured (table.include.list and related options); at a later point in time, it may become necessary to adjust this configuration, so to capture further tables which where not part to CDC initially. That's how incremental snapshot comes into the picture

Academically, you can read [this article](https://arxiv.org/pdf/2010.12597v1.pdf) to see how an incremental snapshot works

<u>For mysql connectors</u>

The engineering team at Shopify recently improved the Debezium MySQL connector so that it supports incremental snapshotting for databases without write access by the connector, which is required when pointing Debezium to read-only replicas (our situation). That is [read-only incremental snapshot](https://debezium.io/blog/2022/04/07/read-only-incremental-snapshots/)

To enable read-only incremental snapshot, add the following properties into the connector config:
```yaml
    signal.kafka.bootstrap.servers: kafka:9092
    signal.kafka.topic: _debezium.signals
    incremental.snapshot.chunk.size: 5000
    read.only: true
```
where: <i>signal.kafka.topic</i> config specifies the signal topic. To trigger an incremental snapshot, just produce a signal record into the signal topic:
```
Key: <record-key>
Value: {"type":"execute-snapshot","data": {"data-collections": ["<database>.<table>,..."], "type": "INCREMENTAL"}}
```
E.g.
```
Key: debezium.vnshop.cov
Value: {"type":"execute-snapshot","data": {"data-collections": ["cov_service.order_invoices"], "type": "INCREMENTAL"}}
```
Refer [this](https://debezium.io/blog/2022/04/07/read-only-incremental-snapshots/) for more information

<u>For postgresql connectors</u>

Read-only incremental snapshot is not supported for postgresql connector so that we have to [create a signal table](https://debezium.io/documentation/reference/stable/configuration/signalling.html#debezium-signaling-creating-a-signal-data-collection) at datasource for sending signal messages. Also, the connectors must have the signal table included in the list of captured tables.

To trigger an incremental snapshot, just execute the following query to insert a signal into the signal table:
```sql
INSERT INTO <signal_topic> (id, type, data) VALUES ('<id>', '<snapshot-type>', '{"data-collections": ["<schema>.<table>,..."],"type":"<snapshot-type>"}');

```
E.g.
```sql
INSERT INTO myschema.debezium_signal (id, type, data) VALUES('adhoc-wms-1', 'execute-snapshot', '{"data-collections": ["wms.stock_warehouse"],"type":"incremental"}');
```

### 3.1. Alert & monitoring
<u>Metrics</u>

<u>Alerts</u>

### 3.5. Clean up and capture changes from scratch
This's simply that an initial snapshot is triggered
- **Step 1**: Stop/Drop debezium connector
```bash
$ kubectl get KafkaConnector -n kafka   # Show existing debezium connectors
$ kubectl delete KafkaConnector <connector-name> -n kafka
```
- **Step 2**: Make previous offset become unavailable for the debezium connector

Debezium connectors store their last committed position in the datasource transaction log in a kafka topic (specified by <i>offset.storage.topic</i> config). On startup, connectors consume messages from the topic, get its lastest offset message and then extract its previous position in transaction log.

If no position information found (no offset message found or the lastest offset message contains no information), the connector will performs an initial snapshot.

So, just produce a offset message to the offset topic:
```
Key: ["<connectopr-name>",{"server":"<connector-server>"}]
Value: null
```
E.g.
```
Key: ["vnshop-wms",{"server":"debezium.vnshop.wms"}]
Value: null
```
- **Step 3** (optional): Remove related topics

If you dont want the tables's topics to contain records produced before the snapshot, remove the topics.

To do that, you have to exec into the kafka pod and wave your magic wand :)))

E.g.
```bash
$ kuberctl exec -it <kafka-podname> -n kafka
[kafka]$ cd ./bin
[kafka]$ ./kafka-topics.sh --bootstrap-server localhost:9092 --delete --topics '.*\.vnshop\.wms\..*'
```

- **Step 4**: Re-deploy debezium connector

Go to [datasource repo's pipeline](https://git.teko.vn/dataops/datasources.next/-/pipelines) and run the lastest successful deploy:connector job

>## 4. Issues & solution
### 4.1. Mysql connector
<u>Kafka Tasks already exists in this worker</u>

[Insert image]
- Reason: Unknown
- Solution:Restart the connect that is handling this task

<u>MySQL Failed to serialize binglog data</u>

[Insert image]
- Reason: Probably related to connections issues
- Solution: Restart the task and change configuration of MySQL to

```sql
set global slave_net_timeout = 120; (default was 30sec)
set global thread_pool_idle_timeout = 120;
```

<u>MySQL Could not open log file</u>

[Insert image]
Try to go to the mysql shell, run these commands
```sql
SHOW BINARY LOGS
SHOW BINLOG EVENTS [IN 'log_name'] [FROM pos] [LIMIT [offset,]
row_count]
```
It will be failed like this

[Insert image]

- Reason: It is likely that the slave DB doesn’t set up correctly, so no binlog file was created
- Solution: Enable [this variable](https://dev.mysql.com/doc/refman/5.6/en/replication-options-binary-log.html#sysvar_log_slave_updates) first.

<u>MySQL Hanging during snapshotting</u>

On snapshotting a big table, it suddenly stopped
- Show processes list on db doesn’t show anything
- The connector’s task still show as RUNNING for long time

[Insert image]

Jstack show hanging at result.next

[Insert image]

For hours it failed and print this error

[Insert image]

Solution:Adding this parameters
- useReadAheadInput: false
- useUnbufferedInput: false

Materials:
- https://bugs.mysql.com/bug.php?id=56411
- https://bugs.mysql.com/bug.php?id=74739
- https://bugs.mysql.com/bug.php?id=24995
- https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-implementation-notes.html 
- https://dev.mysql.com/doc/refman/8.0/en/innodb-disk-io.html

<u>Mysql connectors throw RecordTooLargeException</u>
- Reason: Due to a record too large
- Solution: there are several ways to solve this like using avro (schema registry), proto, etc. But endeding up using topic compression, on testing locally the topic size reduced from 5MB -> 90MB. Setup KafkaConnect with these parameters:
```
  # newly created topics will be compressed using snappy (default: none)
  producer.compression.type: snappy

  # 20MB of a message (default: 1MB), notes
  # - the compression happens on the producer (client's side)
  # - this is the size of the message before being compressed (Kafka bug, since it is unable to compute the message size after compression)
  producer.max.request.size: 20971520
```
Note that the default of topic compression mode in kafka is producer, which lets the producer handle the compression, if set into something else and the producer sends messages in a different compression method, the broker will decompress and recompress data to match with the setup.

<u>MySQL Timezone setup</u>

[Insert image]

- Reason: Missing connector config
- Solution: basically adding this line to the connector setup
```
database.serverTimezone: UTC
```

<u>MySQL Row binlog format</u>

[Insert image here]

DBA problem, they need to configure binlog format to row

<u>Mysql communication link failure</u>

[Insert image here]

Note sure, but above problem get resolved after increasing the timeout of our database gateway
https://git.teko.vn/dataops/baseline/-/blob/master/compose/database-gw/config.d/haproxy.cfg 

<u>MySQL Lock tables</u>

To ensure the consistency of data between different reads during snapshot, Debezium uses Repeatable Read isolation. Basically by starting a transaction before the snapshot and ending that transaction on finish. This is non-locking mode, and doesn't affect other write and reads.

In the first step Debezium needs to read schemas of all tables, to ensure there are no changes in this step, Debezium acquires global lock and will release this lock after reading schemas, therefore the total amount of locking time is minimal.

[Insert image]

Unfortunately, if global lock is failed to acquire, Debezium will lock all the tables but won’t unlock until the snapshot finishes (which can take lots of time), the reason for that is unlock tables will automatically end the transaction above.

The solution for this is quite simple, if we can ensure that schemas won’t change during the first step then just set the `snapshot.locking.mode = none`

### 4.1. Postgresql connector

<u>Postgresql only be able to fetch from primary servers</u>

Materials:
- https://debezium.io/documentation/reference/connectors/postgresql.html#postgresql-cluster-failures
- https://debezium.io/blog/2020/02/25/lessons-learned-running-debezium-with-postgresql-on-rds/

Postgres come with a variety of ways to set up replications. But here are two of them that related to our works
- Streaming replication, basically the slave will replicate everything from the master by reading the master WAL file this gives us a low latency replication solution. The drawbacks of it is the slave is read-only, so it won’t accept any write operations from outside.
- Logical replication, this mode follows the publisher-subscriber paradigm, the master will act like a publisher which can publish changes of all tables or subset of tables. The subscriber can subscribe to change and act upon those changes. The drawbacks
  - all tables that appear on publications need to have primary key (or we need to run some SQL command to publisher can find out how to identify a row or UPDATE/ DELETE queries)
  - Publisher must be master node.
Since Debezium is using logical replication under the hood, it needs to connect to a primary server not slave.
The logical replication does more heavy than streaming replication, but the impact of it is still manageable

<u>Postgresql only admin can create publication</u>

To create a publication (for logical replication) requires admin privileges, normally asking for admin privileges is not acceptable. The solution for this one is asking the DBA to create the publication ahead of time for us and reusing the created publication

<u>Postgresql wal log has already removed</u>

For many reasons, debezium client may be inactive and can not read wal log and send transaction events to the replica via a replication slot which is still over there. By time, wal logs have been removed but replication slot still remember the last read wal log (removed) and that when client starts, it can not read the log file
Solution: Remove replication slot and re-snapshot database (ingest from scratch):
Hot-fix: Resnapshot the whole database
- Remove replication slot: 
```sql
select pg_drop_replication_slot(<slot name>)
```
- [Capture changes from scratch](#35-clean-up-and-capture-changes-from-scratch)

Long-term: Not yet fixed

### 4.3. Common issues
<u>[Debezium connector may some time produce duplicated events](https://debezium.io/documentation/faq/#why_must_consuming_applications_expect_duplicate_events)</u>
