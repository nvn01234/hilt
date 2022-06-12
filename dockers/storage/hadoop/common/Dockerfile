FROM sondn1/base:0.1.0

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

# It is recommmended that pdsh also be installed for better ssh resource management.
RUN apt install -y pdsh

RUN curl -O https://dlcdn.apache.org/hadoop/common/KEYS && gpg --import KEYS

# Download and extract hadoop binary package
ENV HADOOP_URL=https://dlcdn.apache.org/hadoop/common/hadoop-3.3.1/hadoop-3.3.1.tar.gz
RUN set -x \
    && curl -fSL "$HADOOP_URL" -o /tmp/hadoop.tar.gz \
    && curl -fSL "$HADOOP_URL.asc" -o /tmp/hadoop.tar.gz.asc \
    && gpg --verify /tmp/hadoop.tar.gz.asc \
    && mkdir /opt/hadoop \
    && tar -xvf /tmp/hadoop.tar.gz -C /opt/hadoop/ \
    && rm /tmp/hadoop.tar.gz*

RUN mkdir /hadoop-data

ENV USER=root
ENV HADOOP_HOME=/opt/hadoop/hadoop-3.3.1
ENV HADOOP_CONF_DIR=/opt/hadoop/hadoop-3.3.1/etc/hadoop
ENV PATH=$HADOOP_HOME/bin/:$PATH

COPY configurations/*.xml $HADOOP_CONF_DIR/
