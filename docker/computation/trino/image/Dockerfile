FROM maven:3.8.5-openjdk-17-slim AS builder

# Building opensearch-event-listener plugin
WORKDIR /trino/plugin/opensearch-event-listener
COPY ./plugins/opensearch-event-listener/pom.xml ./
COPY ./plugins/opensearch-event-listener/src ./src
RUN mvn -f ./pom.xml clean package

# Building trino image
FROM trinodb/trino:408
COPY --from=builder /trino/plugin/opensearch-event-listener/target/opensearch-event-listener-*.jar /usr/lib/trino/plugin/opensearch-event-listener/
