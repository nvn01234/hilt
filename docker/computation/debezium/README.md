# Debezium operating guide

## Connector rest apis
### <u>List connectors</u>
```bash
curl -i -XGET  http://localhost:8083/connectors \
        -H "Accept:application/json"
```
### <u>Create a connector</u>
```bash
curl -i -XPOST http://localhost:8083/connectors \
        -H "Accept:application/json"  \
        -H "Content-Type:application/json" \
        -d <CONNECTOR_CONFIG>
```
where the *CONNECTOR_CONFIG* is a json configuration for the debezium connector
### <u>Update a connector</u>
```bash
curl -i -XPUT  http://localhost:8083/connectors/<CONNECTOR_NAME>/config \
        -H "Accept:application/json" \
        -H "Content-Type:application/json" \
        -d <CONNECTOR_PROPERTIES>
```
wheres, the *CONNECTOR_PROPERTIES* are something like this:
```json
{
  "signal.kafka.topic": "debezium.signals",
  "read.only": true,
  "inconsistent.schema.handling.mode": "warn",
  "database.history.store.only.captured.tables.ddl": true
}
```
The new connector properties would be merge in to the current properties once valid
### Delete a connector
```bash
curl -i -XDELETE http://localhost:8083/connectors/<CONNECTOR_NAME> \
        -H "Accept:application/json"
```

## [Deploy mysql connector](https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-deploying-a-connector)
For connecting to mysql, we would have the following config:
```json
{
  "name": "demo.debezium.mysql.sakila",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "name": "demo.debezium.mysql.sakila",
    "tasks.max": "1",
    "topic.prefix": "debezium.mysql.sakila",
    "database.server.id": 12345,
    "database.hostname": "mysql",
    "database.port": "3306",
    "database.user": "root",
    "database.password": "SuperSecr3t",
    "database.server.name": "debezium.mysql.sakila",
    "database.include.list": "sakila",
    "schema.history.internal.kafka.bootstrap.servers": "kafka:9092",
    "schema.history.internal.kafka.topic": "debezium.mysql.sakila.dbhistory",
    ...
  }
}
```
If you wish to capture changes from a subset of tables, add:
```json
{
  ...
    "table.include.list": "sakila.actor,sakila.film",
    ...
}
```
In practical, we usually add some following extra config to provide a better change capturing:
```json
{
  ...
    "database.history.store.only.captured.tables.ddl": true,
    "bigint.unsigned.handling.mode": "precise",
    "provide.transaction.metadata": "true",
    ...
}
```
If you would like to use a avro schema registry to manage records's schema, just add:
```json
{
  ...
    "key.converter": "io.confluent.connect.avro.AvroConverter",
    "key.converter.schema.registry.url": "http://schema-registry:8085",
    "value.converter": "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url": "http://schema-registry:8085",
    ...
}
```
This config is also valid to apply to other connectors

## [Deploy postgresql connector](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-deployment)
For connecting to postgresql, we would have the following config:
```json
{
  "name": "demo.debezium.postgresql.dvdrental",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "name": "demo.debezium.postgresql.dvdrental",
    "tasks.max": "1",
    "topic.prefix": "debezium.postgresql.dvdrental",
    "database.hostname": "postgresql",
    "database.port": "5432",
    "database.user": "dvdrental",
    "database.password": "SuperSecr3t",
    "database.server.name": "debezium.postgresql.dvdrental",
    "database.dbname": "dvdrental",
    "plugin.name": "pgoutput",
    "schema.include.list": "bookings",
    "slot.name": "dbz_dvdrental",
    "publication.name": "dbz_dvdrental",
    ...
  }
}
```
In practical, we usually add some following extra config to provide a better change capturing:
```json
{
  ...
    "skipped.operations": "none"
    ...
}
```
