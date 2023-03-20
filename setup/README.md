# Setup
Installation commands instruction for essential tools. Here I have:
- [Docker](https://www.docker.com/): a popular containerization tool, you may get fed up of hearing about it
  - [docker desktop](https://www.docker.com/products/docker-desktop/): the fastest way to containerize applications
  - [lazydocker](https://github.com/jesseduffield/lazydocker): a simple terminal UI for both docker and docker-compose
- [Minikube](https://minikube.sigs.k8s.io/docs/): a quick setup of kubernetes cluster for dev environment and should not be used in production 
  - [kubectl](https://kubernetes.io/docs/tasks/tools/#kubectl) and its addons
  - [helm](https://helm.sh/): the best way to find, share, and use software built for kubernetes
  - [helmfile](https://github.com/roboll/helmfile): a package manager for Kubernetes
  - [kustomize](https://kustomize.io/): a template-free way to customize application configuration that simplifies the use of off-the-shelf applications
- [Terraform](https://www.terraform.io/): an infrastructure as code tool powered by HashiCorp that lets you define both cloud and on-prem resources in human-readable configuration files that you can version, reuse, and share
  - [terragrunt](https://terragrunt.gruntwork.io/): a thin wrapper that provides extra tools for keeping your configurations DRY, working with multiple Terraform modules, and managing remote state
- [Taskfile](https://taskfile.dev/): a task runner / build tool that aims to be simpler and easier to use
- [NodeJs](https://nodejs.org/en/): an open-source, cross-platform JavaScript runtime environment
- Languages:
  - [Golang](https://go.dev/): build simple, secure, scalable systems with Go
