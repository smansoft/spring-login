#!/bin/sh

#---------------------------------------------------------------------------
. ./ssl.rsa.ca.ini;
#---------------------------------------------------------------------------
echo "OUT_DIR			=	$OUT_DIR";
echo "CONF_DIR			=	$CONF_DIR";

echo "CA_INTERNAL_CONF_FILE	=	$CA_INTERNAL_CONF_FILE";

echo "CA_PRV_FILE		=	$CA_PRV_FILE";
echo "CA_PUB_NSIGNED_FILE	=	$CA_PUB_NSIGNED_FILE";
echo "CA_PUB_FILE		=	$CA_PUB_FILE";
echo "CA_CSR_FILE		=	$CA_CSR_FILE";

echo "CA_CERT_FILE		=	$CA_CERT_FILE";

echo "RSA_KEY_LENGTH		=	$RSA_KEY_LENGTH";
#---------------------------------------------------------------------------
openssl genrsa -out "$OUT_DIR"/"$CA_PRV_FILE" "$RSA_KEY_LENGTH"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'CA Private Key File'
echo '---------------------------------------------------------------------'
openssl rsa -noout -text -in "$OUT_DIR"/"$CA_PRV_FILE"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
openssl rsa -in "$OUT_DIR"/"$CA_PRV_FILE" -pubout -out "$OUT_DIR"/"$CA_PUB_NSIGNED_FILE"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'CA Non-Signed Public Key File'
echo '---------------------------------------------------------------------'
openssl res -text -pubin -in "$OUT_DIR"/"$CA_PUB_NSIGNED_FILE"
echo '---------------------------------------------------------------------'

# Generating of CA as non-signed request in format PEM 
#---------------------------------------------------------------------------
#openssl req -new -nodes -x509 -config "$CONF_DIR"/"$CA_INTERNAL_CONF_FILE" -key "$OUT_DIR"/"$CA_PRV_FILE" -days 1825 -out "$OUT_DIR"/"$CA_PUB_FILE" -outform pem
#---------------------------------------------------------------------------
#openssl x509 -text -in "$OUT_DIR"/"$CA_PUB_FILE" -out "$OUT_DIR"/"$CA_CERT_FILE"
#---------------------------------------------------------------------------

# Generating of CA as request, then self-sign and saving in format PEM
#---------------------------------------------------------------------------
openssl req -new -nodes -config "$CONF_DIR"/"$CA_INTERNAL_CONF_FILE" -key "$OUT_DIR"/"$CA_PRV_FILE" -out "$OUT_DIR"/"$CA_CSR_FILE"
#---------------------------------------------------------------------------
openssl ca -batch -selfsign -config "$CONF_DIR"/"$CA_INTERNAL_CONF_FILE" -days 1825 -keyfile "$OUT_DIR"/"$CA_PRV_FILE" -in "$OUT_DIR"/"$CA_CSR_FILE" -out "$OUT_DIR"/"$CA_CERT_FILE"
#---------------------------------------------------------------------------
openssl x509 -in "$OUT_DIR"/"$CA_CERT_FILE" -out "$OUT_DIR"/"$CA_PUB_FILE"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'CA Certificate (Self-Signed Public Key) File'
echo '---------------------------------------------------------------------'
openssl x509 -noout -text -in "$OUT_DIR"/"$CA_PUB_FILE"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
