#!/bin/sh

#---------------------------------------------------------------------------
. ./ssl.rsa.sign.ini;
#---------------------------------------------------------------------------
echo "OUT_DIR			=	$OUT_DIR";
echo "CONF_DIR			=	$CONF_DIR";

echo "CA_SERVICES_CONF_FILE	=	$CA_SERVICES_CONF_FILE";

echo "CA_PRV_FILE		=	$CA_PRV_FILE";
echo "CA_CERT_FILE		=	$CA_CERT_FILE";

echo "SRV_PUB_FILE		=	$SRV_PUB_FILE";
echo "SRV_CSR_FILE		=	$SRV_CSR_FILE";
echo "SRV_CERT_FILE		=	$SRV_CERT_FILE";
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
