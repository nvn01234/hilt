docker-compose -f ../docker-compose.yml exec kafka-0 kafka-console-consumer.sh \
    --bootstrap-server kafka-0:9092,kafka-1:9092,kafka-2:9092 \
    --topic $1 \
    --from-beginning \
    --property schema.registry.url=http://localhost:8081 \
    --property key.converter: io.confluent.connect.avro.AvroConverter \
    --property value.converter: io.confluent.connect.avro.AvroConverter \
    --property key.converter.schema.registry.url: http://schema-registry:8081 \
    --property value.converter.schema.registry.url: http://schema-registry:8081