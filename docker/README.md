# Docker
Build your environment using docker / docker-compose

## Supported platforms & Usages
Docker is an open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly. Compose is a tool for defining and running multi-container Docker applications. With Compose, you use a YAML file to configure your application’s services.
Before starting any component, you must [install docker & docker compose](../bookshelf/README.md#setup) and make sure it work fine.

## Usage
Deploying/operating services on docker is quite easy. HILT provides you two ways to interact with services

### Start services one by one:
```bash
task <service>:<action> [-- <param>]
```

where:
- ```<service>```: Service name
- ```<action>``` : Action - start|stop|init. For services that support image customization, actions can be build|push|publish
- ```<param>```  : Specific parameter for some commands (for example: ```task kafka:restart -- kafka``` would just restart kafka-broker instead of all other services come along with it, like schema-registry)

