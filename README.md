# Hilt
![Alt text](./bookshelf/logo/high-resolution-logo_resized400x300.png "Title")

This project is mainly for building a local dev environment

## Directory structure
```
platforms
|
|___+ bookshelf
|   |___+ cheatsheets
|   |___+ libs
|   |___+ setup
|   |___+ draft
|
|___+ compose
|   |___+ base
|   |___+ compute
|   |___+ serving
|   |___+ storage
|   |___- README.md
|   |___- Taskfile.yml
|
|___+ helm
|   |___+ addons
|   |___+ compute
|   |___+ operators
|   |___+ serving
|   |___+ storage
|   |___- helmfile.yaml
|   |___- kustomization.yaml
|   |___- README.md
|   |___- Taskfile.yml
|
|___- README.md

```
- bookshelf
- compose: [Building your environment with docker](./compose/README.md)
  - base: Building your base image that may be helpfull when you want to build your own images
  - compute: Your computation engines, e.g., airflow, nifi, spark, ...
  - serving: Your user facing components (Query interface, Authentication & Authorization, Alert & Monitoring, ...), e.g., keycloak, prometheus, grafana, ...
  - storage: Your storage platforms, e.g., mysql, hadoop, kafka
- helm: [Building your environment with minikube](./helm/README.md)
  - addons: Minikube addons, e.g., cert-manager
  - operator: K8S operators, e.g., strimzi operator, spark operator, ...
  - compute: Your computation engines, e.g., airflow, nifi, spark, ...
  - serving: Your user facing components (Query interface, Authentication & Authorization, Alert & Monitoring, ...), e.g., keycloak, prometheus, grafana, ...
  - storage: Your storage platforms, e.g., mysql, hadoop, kafkasigs.k8s.io/)
## Features
### 1. Local dev/staging environment
### 2. Simplify platforms configurations
### 3. Comfort lazy devs (like me)

