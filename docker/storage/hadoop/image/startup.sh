#!/bin/bash

if [[ "$1" == "namenode" ]]
then
  CLUSTER_NAME=${CLUSTER_NAME:-"hadoop-local"}
  HADOOP_NAME_DIR=${HADOOP_NAME_DIR:-"/data/namenode"}
  mkdir -p $HADOOP_NAME_DIR
  if [ "`ls -A $HADOOP_NAME_DIR`" == "" ]; then
    echo "Formatting namenode name directory: $HADOOP_NAME_DIR"
    $HADOOP_HOME/bin/hdfs --config $HADOOP_CONF_DIR namenode -format $CLUSTER_NAME
  fi
  $HADOOP_HOME/bin/hdfs --config $HADOOP_CONF_DIR namenode

elif [[ "$1" == "datanode" ]]
then
  HADOOP_DATA_DIR=${HADOOP_DATA_DIR:-"/data/datanode"}
  mkdir -p $HADOOP_DATA_DIR
  $HADOOP_HOME/bin/hdfs --config $HADOOP_CONF_DIR datanode

elif [[ "$1" == "nodemanager" ]]
then
  $HADOOP_HOME/bin/yarn --config $HADOOP_CONF_DIR nodemanager
elif [[ "$1" == "resourcemanager" ]]
then
  $HADOOP_HOME/bin/yarn --config $HADOOP_CONF_DIR resourcemanager
elif [[ "$1" == "timelineserver" ]]
then
  HADOOP_TIMELINE_DIR=${HADOOP_TIMELINE_DIR:-"/data/timeline-store"}
  mkdir -p $HADOOP_TIMELINE_DIR
  $HADOOP_HOME/bin/yarn --config $HADOOP_CONF_DIR timelineserver
else
  echo "The component must be one of namenode, datanode, nodemanager, resourcemanager, timelineserver"
fi
