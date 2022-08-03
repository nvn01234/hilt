#!/bin/bash

if [ -z "$CLUSTER_NAME" ]; then
  echo "Cluster name not specified. Set to hadoop-local"
  CLUSTER_NAME=hadoop-local
fi

if [ "`ls -A $HDFS_CONF_NAMENODE_DIR`" == "" ]; then
  echo "Formatting namenode name directory: $HDFS_CONF_NAMENODE_DIR"
  $HADOOP_HOME/bin/hdfs --config $HADOOP_CONF_DIR namenode -format $CLUSTER_NAME
fi

$HADOOP_HOME/bin/hdfs --config $HADOOP_CONF_DIR namenode
