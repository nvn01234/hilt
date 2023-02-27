# --- Clear old certs ---
rm -rf *.pem

# --- Generate CA certificate ---
openssl genrsa -out root-ca-key.pem 2048
openssl req -new -x509 -sha256 -key root-ca-key.pem -subj "/C=VN/ST=Hanoi/L=HN/O=Demo/OU=Demo/CN=opensearch" -out root-ca.pem -days 730
# ---

# --- Generate node certificate ---
openssl genrsa -out node-key-temp.pem 2048
openssl pkcs8 -inform PEM -outform PEM -in node-key-temp.pem -topk8 -nocrypt -v1 PBE-SHA1-3DES -out node-key.pem
openssl req -new -key node-key.pem -subj "/C=VN/ST=Hanoi/L=HN/O=Demo/OU=Demo/CN=opensearch" -out node.csr
echo 'subjectAltName=DNS:opensearch' > node.ext
openssl x509 -req -in node.csr -CA root-ca.pem -CAkey root-ca-key.pem -CAcreateserial -sha256 -out node.pem -days 730 -extfile node.ext
# ---

# Clear
rm -rf node-key-temp.pem node.csr node.ext
