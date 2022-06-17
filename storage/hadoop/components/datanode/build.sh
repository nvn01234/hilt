export HADOOP_DATANODE_VERSION=0.1.0
export HADOOP_VERSION=3.3.1
docker build --tag sondn1/hadoop-datanode:$HADOOP_DATANODE_VERSION-hadoop_$HADOOP_VERSION .
