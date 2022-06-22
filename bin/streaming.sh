source ./script-utils.sh

if [ -z "$1" ]
then
    echo "One of actions up or down is required"
    exit 1
fi

case $1 in
    up)
        service mysql up
        service kafka up
        service schema-registry up docker compose-sr.yml
        service akhq up docker compose-akhq.yml
        service debezium up
        ;;
    down)
        service akhq down docker compose-akhq.yml
        service schema-registry down docker compose-sr.yml
        service kafka down
        service mysql down
        service debezium down
        ;;
    *)
        echo "Streaming flow action must be up or down"
        exit 1
        ;;
esac
