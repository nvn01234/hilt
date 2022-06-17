export SCRIPTS_HOME=$(dirname "$(realpath $0)")

export COMPUTE_HOME=$SCRIPTS_HOME/../compute
export SERVING_HOME=$SCRIPTS_HOME/../serving
export STORAGE_HOME=$SCRIPTS_HOME/../storage

###################################################
#                                                 #
#                     PLATFORMS                   #
#                                                 #
###################################################
export DEBEZIUM_HOME=$COMPUTE_HOME/debezium

export GRAFANA_HOME=$SERVING_HOME/grafana
export PROMETHEUS_HOME=$SERVING_HOME/prometheus

export HADOOP_HOME=$STORAGE_HOME/hadoop
export KAFKA_HOME=$STORAGE_HOME/kafka
export MYSQL_HOME=$STORAGE_HOME/mysql
