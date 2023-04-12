FROM sondn1/hadoop:3.3.5

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

# Allow buildtime config of HIVE_VERSION
ARG HIVE_VERSION=3.1.3

RUN apt update && apt install curl gnupg tar -y

RUN curl -O https://dlcdn.apache.org/hive/KEYS && gpg --import KEYS
RUN set -x \
    && curl -fSL https://dlcdn.apache.org/hive/hive-$HIVE_VERSION/apache-hive-$HIVE_VERSION-bin.tar.gz -o /tmp/hive.tar.gz \
    && curl -fSL https://dlcdn.apache.org/hive/hive-$HIVE_VERSION/apache-hive-$HIVE_VERSION-bin.tar.gz.asc -o /tmp/hive.tar.gz.asc \
    && gpg --verify /tmp/hive.tar.gz.asc \
    && mkdir /opt/hive \
    && tar -xvzf /tmp/hive.tar.gz -C /opt/hive/ \
    && rm /tmp/hive.tar.gz* \
    && mv /opt/hive/apache-hive-${HIVE_VERSION}-bin /opt/hive/apache-hive-bin

ENV HIVE_HOME=/opt/hive/apache-hive-bin
ENV PATH=$HIVE_HOME/bin/:$PATH

# Update postgresql version
RUN rm $HIVE_HOME/lib/postgresql-*.jre7.jar \
    && curl -fSL https://repo1.maven.org/maven2/org/postgresql/postgresql/42.3.6/postgresql-42.3.6.jar -o $HIVE_HOME/lib/postgresql-42.3.6.jar

COPY startup.sh /usr/local/bin/
RUN chmod u+x /usr/local/bin/startup.sh

CMD startup.sh
