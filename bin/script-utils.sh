source ./env.sh

declare -A SERVICES_HOME
# Compute
SERVICES_HOME[debezium]=$DEBEZIUM_HOME
# Serving
SERVICES_HOME[grafana]=$GRAFANA_HOME
SERVICES_HOME[prometheus]=$PROMETHEUS_HOME
# Storage
SERVICES_HOME[hadoop]=$HADOOP_HOME
SERVICES_HOME[kafka]=$KAFKA_HOME
SERVICES_HOME[akhq]=$KAFKA_HOME
SERVICES_HOME[schema-registry]=$KAFKA_HOME
SERVICES_HOME[mysql]=$MYSQL_HOME

function service {
    if [ -z "$1" ] || [ -z "$2" ]
    then
      echo "Service name and action are required"
      exit 1
    fi

    cd ${SERVICES_HOME["$1"]}
    SERVICE_ACTION=$2
    COMPOSE_FILE="${3:-docker-compose.yml}"
    
    case $SERVICE_ACTION in
        up)
            docker compose -f $COMPOSE_FILE up -d
            ;;
        down)
            docker compose -f $COMPOSE_FILE down --remove-orphans
            ;;
        *)
            echo "Service action must be up or down"
            exit 1
            ;;
    esac
}

function create_network {
    NETWORK_NAME="${1:-common_local_network}"
    NETWORK_DRIVER="${2:-bridge}"
    docker network create -d $NETWORK_DRIVER $NETWORK_NAME
}
