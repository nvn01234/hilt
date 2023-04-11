export TASKFILE_VERSION=v3.23.0
# Processor architecture must be one of 386, amd64, arm, and arm64
export PROCESSOR_ARCHITECTURE=amd64
export USER_BINNARY=${USER_BINNARY:-"/usr/local/bin"}

export DOWNLOAD_LINK=https://github.com/go-task/task/releases/download/$TASKFILE_VERSION/task_linux_$PROCESSOR_ARCHITECTURE.tar.gz

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

wget $DOWNLOAD_LINK -O /tmp/task.tar.gz \
&& mkdir -p /tmp/task \
&& tar -xvzf /tmp/task.tar.gz -C /tmp/task \
&& echo $SUDO_PASSWORD | sudo -S mv /tmp/task/task $USER_BINNARY \
&& rm -rf /tmp/task.tar.gz /tmp/task
