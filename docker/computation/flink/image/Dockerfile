FROM flink:1.17.1-scala_2.12-java11

RUN wget -P /opt/flink/plugins https://repo1.maven.org/maven2/org/apache/flink/flink-s3-fs-hadoop/1.17.1/flink-s3-fs-hadoop-1.17.1.jar

COPY ./conf/flink-conf.yaml /opt/flink/conf/flink-conf-custom.yaml
RUN cat /opt/flink/conf/flink-conf.yaml /opt/flink/conf/flink-conf-custom.yaml > /opt/flink/conf/flink-conf-final.yaml
RUN rm /opt/flink/conf/flink-conf.yaml
RUN rm /opt/flink/conf/flink-conf-custom.yaml
RUN mv /opt/flink/conf/flink-conf-final.yaml /opt/flink/conf/flink-conf.yaml

ENV ENABLE_BUILT_IN_PLUGINS=flink-s3-fs-hadoop-1.17.1.jar
