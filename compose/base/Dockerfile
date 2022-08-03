FROM ubuntu:20.04

LABEL maintainer="Do Ngoc Son (dongocson.work@gmail.com)"

RUN apt update && apt install -y \
      openjdk-8-jdk \
      net-tools \
      curl \
      netcat \
      gnupg \
      vim \
      libsnappy-dev
      
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/
