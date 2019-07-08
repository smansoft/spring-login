#!/bin/sh

#---------------------------------------------------------------------------
. ./ssl.rsa.ca.ini;
#---------------------------------------------------------------------------
echo "OUT_DIR			=	$OUT_DIR";
echo "CONF_DIR			=	$CONF_DIR";

echo "CA_SERVICES_CONF_FILE	=	$CA_SERVICES_CONF_FILE";
echo "SRV_CONF_FILE		=	$SRV_CONF_FILE";

echo "CA_PRV_FILE		=	$CA_PRV_FILE";
echo "CA_PUB_NSIGNED_FILE	=	$CA_PUB_NSIGNED_FILE";
echo "CA_PUB_FILE		=	$CA_PUB_FILE";
echo "CA_CSR_FILE		=	$CA_CSR_FILE";
echo "CA_CERT_FILE		=	$CA_CERT_FILE";

echo "SRV_PRV_FILE		=	$SRV_PRV_FILE";
echo "SRV_PUB_NSIGNED_FILE	=	$SRV_PUB_NSIGNED_FILE";
echo "SRV_PUB_FILE		=	$SRV_PUB_FILE";
echo "SRV_CSR_FILE		=	$SRV_CSR_FILE";
echo "SRV_CERT_FILE		=	$SRV_CERT_FILE";

echo "SRV_CHAIN_FILE		=	$SRV_CHAIN_FILE";

echo "SRV_P12_FILE		=	$SRV_P12_FILE";

echo "SRV_P12_PASSWORD		=	$SRV_P12_PASSWORD";
echo "SRV_P12_ALIAS_NAME	=	$SRV_P12_ALIAS_NAME";

echo "RSA_KEY_LENGTH		=	$RSA_KEY_LENGTH";
#---------------------------------------------------------------------------
openssl genrsa -out "$OUT_DIR"/"$SRV_PRV_FILE" "$RSA_KEY_LENGTH"
echo '---------------------------------------------------------------------'
echo 'SRV Private Key File'
echo '---------------------------------------------------------------------'
openssl rsa -noout -text -in "$OUT_DIR"/"$SRV_PRV_FILE"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
openssl rsa -in "$OUT_DIR"/"$SRV_PRV_FILE" -pubout -out "$OUT_DIR"/"$SRV_PUB_NSIGNED_FILE"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'SRV Non-Signed Public Key File'
echo '---------------------------------------------------------------------'
openssl rsa -text -pubin -in "$OUT_DIR"/"$SRV_PUB_NSIGNED_FILE"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
openssl req -config "$CONF_DIR"/"$SRV_CONF_FILE" -new -nodes -key "$OUT_DIR"/"$SRV_PRV_FILE" -out "$OUT_DIR"/"$SRV_CSR_FILE"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'SRV Certificate Request File'
echo '---------------------------------------------------------------------'
openssl req -noout -text -in "$OUT_DIR"/"$SRV_CSR_FILE"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
openssl ca -batch -config "$CONF_DIR"/"$CA_SERVICES_CONF_FILE" -days 1825 -keyfile "$OUT_DIR"/"$CA_PRV_FILE" -cert "$OUT_DIR"/"$CA_CERT_FILE" -in "$OUT_DIR"/"$SRV_CSR_FILE" -out "$OUT_DIR"/"$SRV_CERT_FILE"
#---------------------------------------------------------------------------
openssl x509 -in "$OUT_DIR"/"$SRV_CERT_FILE" -out "$OUT_DIR"/"$SRV_PUB_FILE"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'SRV Certificate (Signed Public Key) File'
echo '---------------------------------------------------------------------'
openssl x509 -noout -text -in "$OUT_DIR"/"$SRV_PUB_FILE"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
if [ -f "$OUT_DIR"/"$SRV_P12_FILE" ]; then
   rm -f "$OUT_DIR"/"$SRV_P12_FILE"
fi;
cat "$OUT_DIR"/"$CA_PUB_FILE" 	> "$OUT_DIR"/"$SRV_CHAIN_FILE"
openssl pkcs12 -export -certfile "$OUT_DIR"/"$SRV_CHAIN_FILE" -in "$OUT_DIR"/"$SRV_PUB_FILE" -inkey "$OUT_DIR"/"$SRV_PRV_FILE" -passout pass:"$SRV_P12_PASSWORD" -name "$SRV_P12_ALIAS_NAME" -out "$OUT_DIR"/"$SRV_P12_FILE"
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'SRV PKCS12 File'
echo '---------------------------------------------------------------------'
openssl pkcs12 -in "$OUT_DIR"/"$SRV_P12_FILE" -info -passin pass:"$SRV_P12_PASSWORD" -passout pass:"$SRV_P12_PASSWORD"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
