export LASTEST_STABLE_VERSION=$(curl -L -s https://dl.k8s.io/release/stable.txt)
export DOWNLOAD_LINK=https://dl.k8s.io/release/$LASTEST_STABLE_VERSION/bin/linux/amd64/kubectl

# Download binary files
curl -Lo /tmp/kubectl $DOWNLOAD_LINK

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

echo $SUDO_PASSWORD | sudo -S install -m 0755 /tmp/kubectl /usr/local/bin/kubectl \
&& rm -rf /tmp/kubectl*

# Install krew - plugins manager for kubectl
KREW_VERSION=v0.4.3
ARCH=amd64
curl -Lo /tmp/krew.tar.gz "https://github.com/kubernetes-sigs/krew/releases/download/$KREW_VERSION/krew-linux_$ARCH.tar.gz"
tar -zxvf /tmp/krew.tar.gz -C /tmp

/tmp/krew-linux_$ARCH install krew

kubectl krew install ns
kubectl krew install ctx

echo '*************************************** Important ************************************'
echo '***Add this line to your .bashrc: export PATH="${KREW_ROOT:-$HOME/.krew}/bin:$PATH"***'
echo '**************************************************************************************'
