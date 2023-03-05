export TERRAGRUNT_VERSION=v0.42.7
export OS=linux
export ARCH=amd64 # One of 386, amd64, arm, and arm64
export USER_BINNARY=/usr/local/bin

export DOWNLOAD_LINK=https://github.com/gruntwork-io/terragrunt/releases/download/${TERRAGRUNT_VERSION}/terragrunt_${OS}_${ARCH}

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

curl -o /tmp/terragrunt $DOWNLOAD_LINK \
&& chmod u+x /tmp/terragrunt \
&& echo $SUDO_PASSWORD | sudo -S mv /tmp/terragrunt $USER_BINNARY
