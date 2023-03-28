#/bin/bash
DOWNLOAD_LINK=https://raw.githubusercontent.com/sondn98/hilt/master/kubernetes/databases/postgresql/sample/dvdrental/dvdrental.tar
curl $DOWNLOAD_LINK -o /tmp/dvdrental.tar

PGPASSWORD='SuperSecr3t' createdb   --username=postgres dvdrental
PGPASSWORD='SuperSecr3t' pg_restore --username=postgres -d dvdrental /tmp/dvdrental.tar
rm -rf /tmp/dvdrental.tar
