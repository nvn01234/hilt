docker-compose -f ../docker-compose.yml exec kafka bin/kafka-console-producer.sh \
    --bootstrap-server kafka:9092 \
    --property "parse.key=true" \
    --property "key.serializer=org.apache.kafka.common.serialization.StringSerializer" \
    --property "value.serializer=org.apache.kafka.common.serialization.StringSerializer" \
    --property "key.separator=;" \
    --topic debezium.signals
# debezium.mysql.sakila;{"type":"execute-snapshot","data": {"data-collections": ["sakila.film_text"], "type": "INCREMENTAL"}}