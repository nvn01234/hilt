FROM sondn1/hadoop-base:0.1.0-hadoop_3.3.1

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

HEALTHCHECK CMD curl -f http://localhost:8042/ || exit 1

EXPOSE 8042

CMD $HADOOP_HOME/bin/yarn --config $HADOOP_CONF_DIR nodemanager
