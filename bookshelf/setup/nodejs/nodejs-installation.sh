export NODEJS_VERSION=v16.17.1
export DOWNLOAD_LINK=https://nodejs.org/dist/${NODEJS_VERSION}/node-${NODEJS_VERSION}-linux-x64.tar.xz

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

wget "$DOWNLOAD_LINK" -O /tmp/nodejs.tar.xz
mkdir -p ~/Software
tar -xvf /tmp/nodejs.tar.xz -C ~/Software
mv ~/Software/node-v16.17.1-linux-x64 ~/Software/nodejs
echo $SUDO_PASSWORD | sudo -S ln -s ~/Software/nodejs/bin/node /usr/bin/node

echo '*************************************** Important ************************************'
echo '***    Add this line to your .bashrc: export PATH="~/Software/nodejs/bin:$PATH"    ***'
echo '**************************************************************************************'
