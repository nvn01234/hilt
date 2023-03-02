from flask import Flask, request
import json
import requests
import os
import logging

logging.basicConfig(level = logging.INFO, format='[%(asctime)s] {%(filename)s:%(lineno)d} %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

OPENSEARCH_BASE_URL = 'https://opensearch:9200'
OPENSEARCH_INDEX = 'trino_events'
OPENSEARCH_USERNAME = 'admin'
OPENSEARCH_PASSWORD = 'admin'

INDEX_API = f'{OPENSEARCH_BASE_URL}/{OPENSEARCH_INDEX}/_doc'
HEADERS = {
    'Content-Type': 'application/x-ndjson',
}

app = Flask(__name__)

@app.route('/handle', methods=['POST'])
def handle_event():
    event = request.get_json(force=True)
    data = json.dumps(event)
    response = requests.post(INDEX_API,
                            headers=HEADERS,
                            data=data,
                            verify=False,
                            auth=(OPENSEARCH_USERNAME, OPENSEARCH_PASSWORD))
    return "ok"
