FROM ubuntu:22.04

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

ENV HADOOP_VERSION=3.3.5

RUN apt update && apt install -y --no-install-recommends \
                        openjdk-8-jdk \
                        curl \
                        gnupg \
                        tar \
                        libsnappy-dev

ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/

RUN curl -O https://downloads.apache.org/hadoop/common/KEYS && gpg --import KEYS

RUN set -x \
    && curl -fSL https://dlcdn.apache.org/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz -o /tmp/hadoop.tar.gz \
    && curl -fSL https://dlcdn.apache.org/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz.asc -o /tmp/hadoop.tar.gz.asc \
    && gpg --verify /tmp/hadoop.tar.gz.asc \
    && mkdir /opt/hadoop \
    && tar -xvzf /tmp/hadoop.tar.gz -C /opt/hadoop/ \
    && rm /tmp/hadoop.tar.gz* \
    && mv /opt/hadoop/hadoop-${HADOOP_VERSION} /opt/hadoop/apache-hadoop-bin

ENV HADOOP_HOME=/opt/hadoop/apache-hadoop-bin
ENV HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
ENV PATH=$HADOOP_HOME/bin/:$PATH

RUN mkdir -p /data/logs

COPY startup.sh /usr/local/bin/
RUN chmod u+x /usr/local/bin/startup.sh
