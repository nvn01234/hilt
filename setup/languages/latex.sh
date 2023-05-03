echo "This installation requires password for sudo command"
echo "Password for sudo command: "
read SUDO_PASSWORD

export TEX_DIR=${TEX_DIR:-"/data/sondn/software/latex"} && mkdir -p $TEX_DIR

echo $SUDO_PASSWORD | sudo -S apt update
curl -Lo /tmp/install-tl-unx.tar.gz http://mirror.ctan.org/systems/texlive/tlnet/install-tl-unx.tar.gz
tar -zxvf /tmp/install-tl-unx.tar.gz -C /tmp
mv /tmp/install-tl-*/ /tmp/install-texlive

/tmp/install-texlive/install-tl --texdir=$TEX_DIR --no-interaction

echo '*************************************** Important ************************************'
echo '*** Add this line to your .bashrc:                         ***'
echo '*** - export PATH="$TEX_DIR/bin/x86_64-linux:$PATH"        ***'
echo '*** - export MANPATH="$TEX_DIR/texmf-dist/doc/man:$PATH"   ***'
echo '*** - export INFOPATH="$TEX_DIR/texmf-dist/doc/info:$PATH" ***'
echo '**************************************************************************************'
