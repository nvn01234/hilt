export TERRAFORM_VERSION=1.2.5
export PROCESSOR_ARCHITECTURE=amd64 # One of 386, amd64, arm, and arm64
export USER_BINNARY=/usr/local/bin

export DOWNLOAD_LINK=https://releases.hashicorp.com/terraform/{$TERRAFORM_VERSION}/terraform_{$TERRAFORM_VERSION}_linux_{$PROCESSOR_ARCHITECTURE}.zip

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

curl -o /tmp/terraform.zip $DOWNLOAD_LINK \
&& echo $SUDO_PASSWORD | sudo -S unzip /tmp/terraform.zip -d $USER_BINNARY \
&& rm -rf /tmp/terraform.zip
