# Hilt
![Alt text](./bookshelf/logo/high-resolution-logo_resized400x300.png "Title")

This project is mainly for building a local dev environment

## Features
1. Local dev/staging environment
2. Easy to configure/deploy services
3. Support to deploy services on docker and kubernetes
4. Comfort lazy devs (like me)

## Supported services
| Idx | Service              | Kubernetes | Docker | Tags |
|-----|----------------------|------------|--------|------|
| 1   | Apache Airflow       | yes        | yes    | #compute #task_runner | 
| 2   | Datahub              | no         | yes    | #compute #data_lineage #visualize | 
| 3   | Debezium             | yes        | yes    | #cdc | 
| 4   | Apache Nifi          | no         | yes    | #compute #task_runner #etl | 
| 5   | Trino                | yes        | yes    | #compute #sql | 
| 6   | Spark                | yes        | yes    | #compute | 
| 7   | Jupyter              | no         | yes    | #bi | 
| 8   | Superset             | no         | yes    | #bi #visualize | 
| 9   | Clickhouse           | no         | yes    | #database #sql | 
| 10  | Mongo DB             | no         | yes    | #database #nosql | 
| 11  | Mysql                | yes        | yes    | #database #sql | 
| 12  | Elasticsearch        | yes        | yes    | #database #cdc | 
| 13  | Opensearch           | yes        | yes    | #database #cdc | 
| 14  | Neo4j                | no         | yes    | #database #graph | 
| 15  | Postgresql           | yes        | yes    | #database #sql | 
| 16  | Rabbit MQ            | no         | yes    | #database #key_value | 
| 17  | Redis                | yes        | yes    | #database #key_value #inmem | 
| 18  | Grafana              | yes        | yes    | #monitor #visualize | 
| 19  | Grafana on-call      | no         | yes    | #monitor #visualize | 
| 20  | Prometheus           | yes        | yes    | #monitor #metrics | 
| 21  | Fluentbit            | no         | no     | #monitor #metrics #log #collector | 
| 22  | Keycloak             | yes        | yes    | #security #auth | 
| 23  | Vault                | no         | yes    | #security #secret | 
| 24  | Apache Kafka         | yes        | yes    | #storage #cdc | 
| 25  | Minio                | yes        | yes    | #storage #fs | 
| 26  | Apache Zookeeper     | yes        | yes    | #storage #coordinator | 
| 27  | Apache Hive          | yes        | yes    | #warehouse |

## Usage
### Deploy on docker
### Deploy on kubernetes

## Directory structure
```
HILT
|
|___+ bookshelf                 
|   |___+ cheatsheets           
|   |___+ logo                  
|   |___+ draft                 
|
|___+ docker                    
|   |___+ computation            
|   |___+ data-mining                 
|   |___+ databases                 
|   |___+ monitoring                  
|   |___+ security                  
|   |___+ storage                 
|   |___+ warehouse                 
|   |___+ other                  
|   |___- cmds.yml                  
|   |___- Taskfile.yml               
|   |___- README.md        
|
|___+ kubernetes                 
|   |___+ addons                  
|   |___+ computation                 
|   |___+ databases                 
|   |___+ monitoring                  
|   |___+ operators                 
|   |___+ security                  
|   |___+ storage                 
|   |___+ warehouse                 
|   |___- helmfile.yaml                 
|   |___- kustomization.yaml        
|   |___- Taskfile.yml                        
|   |___- README.md    
|
|___- README.md                 

```
