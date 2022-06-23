export HADOOP_RESOURCEMANAGER_VERSION=0.1.0
export HADOOP_VERSION=3.3.1
docker build --tag sondn1/hadoop-resourcemanager:$HADOOP_RESOURCEMANAGER_VERSION-hadoop_$HADOOP_VERSION .
docker push sondn1/hadoop-resourcemanager:$HADOOP_RESOURCEMANAGER_VERSION-hadoop_$HADOOP_VERSION
