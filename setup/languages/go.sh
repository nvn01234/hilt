export GOLANG_VERSION=1.19.4
# Processor architecture must be one of amd64, arm64, amd64, 386
export ARCH=amd64
# OS must be one of darwin, linux, windows
export OS=linux

export LOCAL_LIBS=/usr/local
export DOWNLOAD_LINK=https://go.dev/dl/go${GOLANG_VERSION}.${OS}-${ARCH}.tar.gz

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

wget $DOWNLOAD_LINK -O /tmp/golang.tar.gz \
&& echo $SUDO_PASSWORD | sudo -S rm -rf ${LOCAL_LIBS}/go \
&& echo $SUDO_PASSWORD | sudo -S tar -C ${LOCAL_LIBS} -xvzf /tmp/golang.tar.gz

echo '******************************* Important ******************************'
echo '***Add this line to your .bashrc: export PATH=$PATH:/usr/local/go/bin***'
echo '************************************************************************'
