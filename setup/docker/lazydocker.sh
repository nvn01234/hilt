export LAZYDOCKER_VERSION=0.20.0
# Processor architecture must be one of armv7, armv6, amd64, arm64
export ARCH=x86_64
# OS must be one of Darwin, Linux, Windows
export OS=Linux

export USER_BINNARY=/usr/local/bin
export DOWNLOAD_LINK=https://github.com/jesseduffield/lazydocker/releases/download/v${LAZYDOCKER_VERSION}/lazydocker_${LAZYDOCKER_VERSION}_${OS}_${ARCH}.tar.gz

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

wget $DOWNLOAD_LINK -O /tmp/lazydocker.tar.gz \
&& mkdir -p /tmp/lazydocker \
&& tar -xvzf /tmp/lazydocker.tar.gz -C /tmp/lazydocker \
&& echo $SUDO_PASSWORD | sudo -S mv /tmp/lazydocker/lazydocker $USER_BINNARY \
&& rm -rf /tmp/lazydocker.tar.gz /tmp/lazydocker
