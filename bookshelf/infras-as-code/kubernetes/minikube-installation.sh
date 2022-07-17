export MINIKUBE_VERSION=v1.26.0
export ARCH=amd64
export DOWNLOAD_LINK=https://github.com/kubernetes/minikube/releases/download/$MINIKUBE_VERSION/minikube-linux-$ARCH

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

curl -Lo /tmp/minikube $DOWNLOAD_LINK
echo $SUDO_PASSWORD | sudo -S install /tmp/minikube /usr/local/bin/minikube

minikube start
