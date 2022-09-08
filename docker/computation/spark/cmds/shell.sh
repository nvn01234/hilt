ICEBERG_VERSION=0.13.2
docker-compose exec -it spark spark-shell --packages org.apache.iceberg:iceberg-spark-runtime-3.2_2.12:$ICEBERG_VERSION
