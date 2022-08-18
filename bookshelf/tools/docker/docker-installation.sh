# This installation is applied for ubuntu only
# Refer https://docs.docker.com/engine/install/binaries/ to see how to install docker with binaries

echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

# Update the apt package index and install packages to allow apt to use a repository over HTTPS
echo $SUDO_PASSWORD | sudo -S apt remove docker docker-engine docker.io containerd runc

# Add Docker’s official GPG key
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg lsb-release

# Use the following command to set up the repository
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Install docker engine
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Manage docker as a non-root user. Reboot or re-login may be required after this installation
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker

echo "******************************************************************"
echo "*** Reboot or re-login may be required after this installation ***"
echo "******************************************************************"
