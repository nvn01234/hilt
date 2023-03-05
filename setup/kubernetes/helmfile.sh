export HELMFILE_VERSION=v0.144.0

# Processor architecture must be one of 386, amd64, and arm64
export PROCESSOR_ARCHITECTURE=amd64 
export USER_BINNARY=/usr/local/bin

export DOWNLOAD_LINK=https://github.com/roboll/helmfile/releases/download/$HELMFILE_VERSION/helmfile_linux_$PROCESSOR_ARCHITECTURE

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

echo $SUDO_PASSWORD | sudo -S wget $DOWNLOAD_LINK -O $USER_BINNARY/helmfile
sudo chmod 755 $USER_BINNARY/helmfile
