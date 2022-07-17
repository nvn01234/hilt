| Index | Description | Command |
|-------|-------------|---------|
|1|Docker container IP|docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_name_or_id>|
|2|Remove stopped containers|docker rm $(docker ps -aq)|
