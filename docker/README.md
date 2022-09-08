# Compose
Build your environment using docker / docker-compose
## Supported platforms & Usages
Docker is an open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly. Compose is a tool for defining and running multi-container Docker applications. With Compose, you use a YAML file to configure your application’s services.
Before starting any component, you must [install docker & docker compose](../bookshelf/README.md#setup) and make sure it work fine.
### Computation engines
<table>
    <thead>
        <tr>
            <th>Platform</th>
            <th>Command</th>
            <th>Description</th>
            <th>Note</th>
        </tr>
    </thead>
    <tbody>
        <!-- Airflow -->
        <tr>
            <td rowspan=2>Airflow</td>
            <td>start</td>
            <td>Start 1 airflow cluster</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the airflow cluster</td>
        </tr>
        <!-- Debezium -->
        <tr>
            <td rowspan=8>Debezium</td>
            <td>start</td>
            <td>Start 1 debezium connect cluster</td>
            <td rowspan=8>Refer ... for more details</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the debezium connect cluster</td>
        </tr>
        <tr>
            <td>create-connector</td>
            <td>Create a debezium connector</td>
        </tr>
        <tr>
            <td>delete-connector</td>
            <td>Delete a debezium connector</td>
        </tr>
        <tr>
            <td>get-connector</td>
            <td>Get a debezium connector configurations</td>
        </tr>
        <tr>
            <td>list-connectors</td>
            <td>list all debezium connectors</td>
        </tr>
        <tr>
            <td>restart-connector</td>
            <td>Restart a debezium connector</td>
        </tr>
        <tr>
            <td>update-connector</td>
            <td>Update a debezium connector configuration</td>
        </tr>
        <!-- Nifi -->
        <tr>
            <td rowspan=3>Nifi</td>
            <td>start</td>
            <td>Start 1 nifi cluster</td>
            <td rowspan=3></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the nifi cluster</td>
        </tr>
        <tr>
            <td>get-jars</td>
            <td>Get external jar files (like mysql-connector-java, trino-jdbc, ...)</td>
        </tr>
        <!-- Spark -->
        <tr>
            <td rowspan=2>Spark</td>
            <td>start</td>
            <td>Start 1 spark live server and its workers</td>
            <td rowspan=2></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the spark cluster</td>
        </tr>
        <!-- Trino -->
        <tr>
            <td rowspan=2>Trino</td>
            <td>start</td>
            <td>Start 1 spark live server and its workers</td>
            <td rowspan=2></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the spark cluster</td>
        </tr>
    </tbody>
</table>

### Serving components
<table>
    <thead>
        <tr>
            <th>Platform</th>
            <th>Command</th>
            <th>Description</th>
            <th>Note</th>
        </tr>
    </thead>
    <tbody>
        <!-- Grafana -->
        <tr>
            <td rowspan=2>Grafana</td>
            <td>start</td>
            <td>Start grafana server</td>
            <td rowspan=2></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop grafana server</td>
        </tr>
        <!-- Hive -->
        <tr>
            <td rowspan=4>Hive</td>
            <td>start</td>
            <td>Start hive</td>
            <td rowspan=4></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop hive</td>
        </tr>
        <tr>
            <td>shell</td>
            <td>Enter hive command cli</td>
        </tr>
        <tr>
            <td>build</td>
            <td>Build Hive image</td>
        </tr>
        <!-- Prometheus -->
        <tr>
            <td rowspan=2>Prometheus</td>
            <td>start</td>
            <td>Start prometheus server</td>
            <td rowspan=2></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop prometheus server</td>
        </tr>
        <!-- Keycloak -->
        <tr>
            <td rowspan=2>Keycloak</td>
            <td>start</td>
            <td>Start keycloak server</td>
            <td rowspan=2></td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop keycloak server</td>
        </tr>
    </tbody>
</table>

### Storage platforms
<table>
    <thead>
        <tr>
            <th>Platform</th>
            <th>Command</th>
            <th>Description</th>
            <th>Note</th>
        </tr>
    </thead>
    <tbody>
        <!-- Hadoop -->
        <tr>
            <td rowspan=9>Hadoop</td>
            <td>start-all</td>
            <td>Start a hadoop cluster</td>
            <td rowspan=9></td>
        </tr>
        <tr>
            <td>stop-all</td>
            <td>Stop the hadoop cluster</td>
        </tr>
        <tr>
            <td>start-hdfs</td>
            <td>Start only hdfs components (namenode & datanode)</td>
        </tr>
        <tr>
            <td>stop-hdfs</td>
            <td>Stop the HDFS (namenode & datanode)</td>
        </tr>
        <tr>
            <td>build-namenode</td>
            <td>Build namenode image</td>
        </tr>
        <tr>
            <td>build-datanode</td>
            <td>Build datanode image</td>
        </tr>
        <tr>
            <td>build-historyserver</td>
            <td>Build history server image</td>
        </tr>
        <tr>
            <td>build-nodemanager</td>
            <td>Build node manager image</td>
        </tr>
        <tr>
            <td>build-resourcemanager</td>
            <td>Build resource manager image</td>
        </tr>
        <!-- Kafka -->
        <tr>
            <td rowspan=6>Kafka</td>
            <td>start-all</td>
            <td>Start a kafka cluster including kafka broker, akhq and schema registry</td>
            <td rowspan=9></td>
        </tr>
        <tr>
            <td>stop-all</td>
            <td>Stop the kafka cluster</td>
        </tr>
        <tr>
            <td>start-kafka</td>
            <td>Start a kafka cluster without schema registry</td>
        </tr>
        <tr>
            <td>stop-kafka</td>
            <td>Stop the kafka cluster without schema registry</td>
        </tr>
        <tr>
            <td>start-schema-registry</td>
            <td>Start schema registry</td>
        </tr>
        <tr>
            <td>stop-schema-registry</td>
            <td>Stop schema registry</td>
        </tr>
        <!-- Minio -->
        <tr>
            <td rowspan=2>Minio</td>
            <td>start</td>
            <td>Start a minio cluster</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the minio cluster</td>
        </tr>
        <!-- Mongo -->
        <tr>
            <td rowspan=2>Mongo</td>
            <td>start</td>
            <td>Start mongo database</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop mongo database</td>
        </tr>
        <!-- Mysql -->
        <tr>
            <td rowspan=3>Mysql</td>
            <td>start</td>
            <td>Start mysql database</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop mysql database</td>
        </tr>
        <tr>
            <td>shell</td>
            <td>Enter mysql command cli</td>
        </tr>
        <!-- Postgresql -->
        <tr>
            <td rowspan=3>Postgresql</td>
            <td>start</td>
            <td>Start postgresql database</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop postgresql database</td>
        </tr>
        <tr>
            <td>shell</td>
            <td>Enter postgresql command cli</td>
        </tr>
        <!-- RabbitMQ -->
        <tr>
            <td rowspan=2>RabbitMQ</td>
            <td>start</td>
            <td>Start RabbitMQ database</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop RabbitMQ database</td>
        </tr>
        <!-- Redis -->
        <tr>
            <td rowspan=2>Redis</td>
            <td>start</td>
            <td>Start Redis server</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop Redis server</td>
        </tr>
        <!-- Zookeeper -->
        <tr>
            <td rowspan=2>Zookeeper</td>
            <td>start</td>
            <td>Start a zookeeper Quorum</td>
        </tr>
        <tr>
            <td>stop</td>
            <td>Stop the zookeeper Quorum</td>
        </tr>
    </tbody>
</table>
