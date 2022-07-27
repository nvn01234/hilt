FROM sondn1/hadoop-base:0.1.0-hadoop_3.3.1

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

HEALTHCHECK CMD curl -f http://localhost:8042/ || exit 1

ADD run.sh /run.sh
RUN chmod a+x /run.sh

EXPOSE 8042

CMD ["/run.sh"]
