export KUSTOMIZE_VERSION=v4.5.7
export OS=linux
export ARCH=amd64
export DOWNLOAD_LINK=https://github.com/kubernetes-sigs/kustomize/releases/download/kustomize%2F${KUSTOMIZE_VERSION}/kustomize_${KUSTOMIZE_VERSION}_${OS}_${ARCH}.tar.gz

wget $DOWNLOAD_LINK -O /tmp/kustomize.tar.gz \
&& tar -zxvf /tmp/kustomize.tar.gz -C /tmp

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

echo $SUDO_PASSWORD | sudo -S install -m 0755 /tmp/kustomize /usr/local/bin/kustomize \
&& rm -rf /tmp/kustomize*
