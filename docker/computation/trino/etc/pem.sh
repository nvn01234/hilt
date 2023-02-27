# --- Clear old cert ---
rm -rf *.pem *.p12

# --- Trino client cert ---
openssl genrsa -out trino-key-temp.pem 2048
openssl pkcs8 -inform PEM -outform PEM -in trino-key-temp.pem -topk8 -nocrypt -v1 PBE-SHA1-3DES -out trino-key.pem
openssl req -new -key trino-key.pem -subj "/C=VN/ST=Hanoi/L=HN/O=Demo/OU=Demo/CN=trino" -out trino.csr
echo 'subjectAltName=DNS:trino' > trino.ext

CA_CERT_DIR=../../../storage/opensearch/config/opensearch
cp $CA_CERT_DIR/root-ca.pem ./
cp $CA_CERT_DIR/root-ca-key.pem ./
openssl x509 -req -in trino.csr -CA root-ca.pem -CAkey root-ca-key.pem -CAcreateserial -sha256 -out trino.pem -days 730 -extfile trino.ext

openssl pkcs12 -export -out trino.p12 -inkey trino-key.pem -in trino.pem -password pass:SuperSecr3t

openssl pkcs8 -inform PEM -outform PEM -in root-ca-key.pem -topk8 -nocrypt -v1 PBE-SHA1-3DES -out root-key.pem
openssl pkcs12 -export -out root.p12 -inkey root-key.pem -in root-ca.pem -password pass:SuperSecr3t

# Clear
rm -rf trino-key-temp.pem trino.csr trino.ext
