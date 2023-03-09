FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /trino/plugin
COPY pom.xml ./
COPY src ./src
RUN mvn -f ./pom.xml clean package

FROM trinodb/trino:408
COPY --from=builder /trino/plugin/target/opensearch-event-listener-*.jar /usr/lib/trino/plugin/opensearch-event-listener/
