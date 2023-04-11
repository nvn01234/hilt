export HELM_VERSION=v3.9.1
# Processor architecture must be one of 386, amd64, arm, arm64, ppc64le, and s390x
export PROCESSOR_ARCHITECTURE=amd64
export USER_BINNARY=${USER_BINNARY:-"/usr/local/bin"}

export DOWNLOAD_LINK=https://get.helm.sh/helm-$HELM_VERSION-linux-$PROCESSOR_ARCHITECTURE.tar.gz

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

wget $DOWNLOAD_LINK -O /tmp/helm.tar.gz \
&& mkdir -p /tmp/helm \
&& tar -xvzf /tmp/helm.tar.gz -C /tmp/helm \
&& echo $SUDO_PASSWORD | sudo -S mv /tmp/helm/linux-$PROCESSOR_ARCHITECTURE/helm $USER_BINNARY \
&& rm -rf /tmp/helm.tar.gz /tmp/helm
