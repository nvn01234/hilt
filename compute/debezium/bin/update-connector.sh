curl -i -X PUT http://localhost:8083/connectors/$1/config \
    -H "Accept:application/json" \
    -H "Content-Type:application/json" \
    -d @../sample/update-connector-mysql.json
