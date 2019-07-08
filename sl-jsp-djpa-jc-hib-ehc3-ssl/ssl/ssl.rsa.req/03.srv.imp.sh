#!/bin/sh

#---------------------------------------------------------------------------
. ./ssl.rsa.req.ini;
#---------------------------------------------------------------------------
echo "OUT_DIR			=	$OUT_DIR";
echo "CONF_DIR			=	$CONF_DIR";

echo "CA_PUB_FILE		=	$CA_PUB_FILE";
echo "CA_CERT_FILE		=	$CA_CERT_FILE";

echo "SRV_PRV_FILE		=	$SRV_PRV_FILE";
echo "SRV_PUB_FILE		=	$SRV_PUB_FILE";
echo "SRV_CERT_FILE		=	$SRV_CERT_FILE";

echo "SRV_CHAIN_FILE		=	$SRV_CHAIN_FILE";

echo "SRV_P12_FILE		=	$SRV_P12_FILE";
echo "SRV_P12_PASSWORD		=	$SRV_P12_PASSWORD";
echo "SRV_P12_ALIAS_NAME	=	$SRV_P12_ALIAS_NAME";
#---------------------------------------------------------------------------
if [ -f "$OUT_DIR"/"$CA_CERT_FILE" ] && [ ! -f "$OUT_DIR"/"$CA_PUB_FILE" ]; then
	openssl x509 -in "$OUT_DIR"/"$CA_CERT_FILE" -out "$OUT_DIR"/"$CA_PUB_FILE"
fi;
#---------------------------------------------------------------------------
if [ -f "$OUT_DIR"/"$SRV_CERT_FILE" ] && [ ! -f "$OUT_DIR"/"$SRV_PUB_FILE" ]; then
	openssl x509 -in "$OUT_DIR"/"$SRV_CERT_FILE" -out "$OUT_DIR"/"$SRV_PUB_FILE"
fi;
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

if [ -f "$OUT_DIR"/"$CA_PUB_FILE" ]; then
	cat "$OUT_DIR"/"$CA_PUB_FILE" 	> "$OUT_DIR"/"$SRV_CHAIN_FILE"
	openssl pkcs12 -export -certfile "$OUT_DIR"/"$SRV_CHAIN_FILE" -in "$OUT_DIR"/"$SRV_PUB_FILE" -inkey "$OUT_DIR"/"$SRV_PRV_FILE" -passout pass:"$SRV_P12_PASSWORD" -name "$SRV_P12_ALIAS_NAME" -out "$OUT_DIR"/"$SRV_P12_FILE"
else
	openssl pkcs12 -export -in "$OUT_DIR"/"$SRV_PUB_FILE" -inkey "$OUT_DIR"/"$SRV_PRV_FILE" -passout pass:"$SRV_P12_PASSWORD" -name "$SRV_P12_ALIAS_NAME" -out "$OUT_DIR"/"$SRV_P12_FILE"
fi;
#---------------------------------------------------------------------------
echo '---------------------------------------------------------------------'
echo 'SRV PKCS12 File'
echo '---------------------------------------------------------------------'
openssl pkcs12 -in "$OUT_DIR"/"$SRV_P12_FILE" -info -passin pass:"$SRV_P12_PASSWORD" -passout pass:"$SRV_P12_PASSWORD"
echo '---------------------------------------------------------------------'
#---------------------------------------------------------------------------
