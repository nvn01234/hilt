source ./script-utils.sh

if [ -z "$1" ]
then
    echo "One of actions up or down is required"
    exit 1
fi

case $1 in
    up)
        service mysql up
        service redis up
        service rabbitmq up
        service grafana up
        service oncall up docker-compose-oncall.yml
        ;;
    down)
        service mysql down
        service redis down
        service rabbitmq down
        service grafana down
        service oncall down docker-compose-oncall.yml
        ;;
    *)
        echo "GF oncall action must be up or down"
        exit 1
        ;;
esac

cd $GRAFANA_HOME
./oncall-setup.sh
