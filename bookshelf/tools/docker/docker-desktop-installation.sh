export DOWNLOAD_LINK=https://desktop.docker.com/linux/main/amd64/docker-desktop-4.10.1-amd64.deb
#?utm_source=docker&utm_medium=webreferral&utm_campaign=docs-driven-download-linux-amd64

wget "$DOWNLOAD_LINK" -O /tmp/docker-desktop.deb

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

echo $SUDO_PASSWORD | sudo -S apt install /tmp/docker-desktop.deb -y
rm -rf /tmp/docker-desktop.deb
