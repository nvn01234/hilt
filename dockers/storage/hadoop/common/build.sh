export HADOOP_BASE_VERSION=0.1.0
export HADOOP_VERSION=3.3.1
docker build --tag sondn1/hadoop-base:$HADOOP_BASE_VERSION-hadoop_$HADOOP_VERSION .
