DATAHUB_GMS_HOST=datahub-gms
DATAHUB_GMS_PORT=8080
DATAHUB_SECRET=SuperSecr3t
DATAHUB_APP_VERSION=1.0
DATAHUB_PLAY_MEM_BUFFER_SIZE=10MB
JAVA_OPTS=\
  -Xms512m \
  -Xmx512m \
  -Dhttp.port=9002 \
  -Dconfig.file=datahub-frontend/conf/application.conf \
  -Djava.security.auth.login.config=datahub-frontend/conf/jaas.conf \
  -Dlogback.configurationFile=datahub-frontend/conf/logback.xml \
  -Dlogback.debug=false -Dpidfile.path=/dev/null
KAFKA_BOOTSTRAP_SERVER=kafka:9092
DATAHUB_TRACKING_TOPIC=DataHubUsageEvent_v1
ELASTIC_CLIENT_HOST=elasticsearch
ELASTIC_CLIENT_PORT=9200
