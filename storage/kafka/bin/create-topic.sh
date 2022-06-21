docker-compose -f ../docker-compose.yml exec kafka-0 kafka-topics.sh \
    --bootstrap-server kafka-0:9092,kafka-1:9092,kafka-2:9092 \
    --create \
    --topic $1 \
    --partitions 1 \
    --replication-factor 1 \