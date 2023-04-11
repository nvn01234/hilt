#/bin/bash
HERE="$(dirname "${BASH_SOURCE[0]}")"
POSTGRES_USER="${POSTGRES_USER:-postgres}"

PGPASSWORD=SuperSecr3t createdb   --username=${POSTGRES_USER}    dvdrental
PGPASSWORD=SuperSecr3t pg_restore --username=${POSTGRES_USER} -d dvdrental $HERE/dvdrental.tar
