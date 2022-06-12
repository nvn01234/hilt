docker compose -f ../storage/mysql/docker-compose.yml \
               -f ../storage/kafka/docker-compose.yml \
               -f ../storage/kafka/docker-compose.yml \
               -f ../storage/kafka/docker-compose-sr.yml \
               -f ../storage/kafka/docker-compose-akhq.yml \
               -f ../compute/debezium/docker-compose.yml \
               up -d

sleep 10s
cd ../compute/debezium
./create-connector.sh 