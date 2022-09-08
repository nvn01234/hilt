# Helm
Build your environment using Helm / Helmfile
## Supported platforms & Usages
Helm & helmfile simplifies deployments very well that you just have to deploy your whole system in one command. Before all, you need to [setup](../bookshelf/README.md#setup) a kubernetes cluster and kubectl. In this project, I use minikube with virtualbox driver which quickly sets up a local Kubernetes cluster on MacOS, Linux, and Windows. Virtualbox driver may not provide the fastest start-up time, but it is the most stable driver.
### Start minikube
```bash
$ task minikube-up
```
The ```minikube start``` command accompanies with many option including resource options (storage, cpu, ram). You can adjust the options by setting the following environment variables:
```bash
export CLUSTER_MOUNTPATH=<your_mount_path>  # Default: /data1/minikube
export CLUSTER_MEMORY=<memory_size_in_mb>   # Default: 8192
export CLUSTER_CPUS=<number_of_cpus>        # Default: 4
```
### Deploy components
Helmfile is a declarative spec for deploying helm charts. The default name for a helmfile is helmfile.yaml. In the ```helmfiles``` section, uncomment components you want to deploy. For example:
```yaml
...
helmfiles:

# - path: addons/cert-manager/helmfile.yaml

# - path: operators/strimzi/helmfile.yaml
# - path: compute/airflow/helmfile.yaml
# - path: compute/trino/helmfile.yaml

# - path: serving/grafana/helmfile.yaml
# - path: serving/keycloak/helmfile.yaml
# - path: serving/hive/helmfile.yaml
# - path: serving/prometheus/helmfile.yaml

- path: storage/kafka/helmfile.yaml
# - path: storage/postgresql/helmfile.yaml
# - path: storage/minio/helmfile.yaml
# - path: storage/mysql/helmfile.yaml
- path: storage/zookeeper/helmfile.yaml
```
Kustomize provides a solution for customizing Kubernetes resource configuration free from templates and DSLs. Edit the ```kustomization.yaml``` file to apply the essential free resource files to your k8s

Run the following command to deploy your system:
```bash
$ task deploy-all
```
