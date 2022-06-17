docker compose -f ../docker-compose.yml exec kafka-0 kafka-console-producer.sh \
    --bootstrap-server kafka-0:9092,kafka-1:9092,kafka-2:9092 \
    --property "parse.key=true" \
    --property "key.serializer=org.apache.kafka.common.serialization.StringSerializer" \
    --property "value.serializer=custom.class.serialization.JsonSerializer" \
    --property "key.separator=;" \
    --topic debezium.sakila.signals
    --create
# debezium.mysql.sakila;{"type":"execute-snapshot","data": {"data-collections": ["sakila.city"], "type": "INCREMENTAL"}}