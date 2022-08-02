export MINIKUBE_VERSION=v1.26.0
export ARCH=amd64
export DOWNLOAD_LINK=https://github.com/kubernetes/minikube/releases/download/$MINIKUBE_VERSION/minikube-linux-$ARCH

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

curl -Lo /tmp/minikube $DOWNLOAD_LINK
echo $SUDO_PASSWORD | sudo -S install /tmp/minikube /usr/local/bin/minikube

echo "******************************* WARNING ********************************"
echo "The Docker driver allows you to install Kubernetes into an existing Docker install. On Linux, this does not require virtualization to be enabled."
echo "The following requirements must be fulfilled:"
echo "- Docker 20.10 or higher"
echo "- Cgroup v2 delegation"
echo "- Kernel 5.11 or later (5.13 or later is recommended when SELinux is enabled)"
read -p "If these requirements has been fulfilled, press ENTER to continue. Else, press any button to cancel" CONTINUE

if [ ${#CONTINUE} -ne 0 ]; then
  echo "OK, bye"
  exit 0
fi

dockerd-rootless-setuptool.sh install -f
docker context use rootless

# Start minikube
minikube start --driver=docker \
               --mount \
               --mount-string="/data1/minikube:/data1" \
               --container-runtime=containerd \
               --memory 8192 --cpus 4 \
               --nodes 1

# Stop minikube
minikube stop --all

# Delete minikube containers
minikube delete
