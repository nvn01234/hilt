FROM sondn1/hadoop-base:0.1.0-hadoop_3.3.1

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

HEALTHCHECK CMD curl -f http://localhost:9864/ || exit 1

ENV HDFS_CONF_DATANODE_DIR=/hadoop/dfs/data
RUN mkdir -p /hadoop/dfs/data
VOLUME /hadoop/dfs/data

EXPOSE 9864

CMD $HADOOP_HOME/bin/hdfs --config $HADOOP_CONF_DIR datanode
