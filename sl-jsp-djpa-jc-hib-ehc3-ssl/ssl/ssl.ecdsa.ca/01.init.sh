#!/bin/sh

echo ''
echo ''
echo ''
echo ''
echo ''

. ./ssl.ecdsa.ca.ini;

echo "OUT_DIR=$OUT_DIR";

if [ -d "$OUT_DIR" ];
then
    rm -f -r "$OUT_DIR"
fi;

mkdir "$OUT_DIR"

echo 'create dir ./new_certs...'
echo '---------------------------------------------------------------------'

if [ -d ./new_certs ];
then
	rm -f -r ./new_certs
fi;

mkdir ./new_certs

echo 'create file serial...'
echo '---------------------------------------------------------------------'

if [ -f ./serial ];
then
	rm ./serial.*	
fi;

touch serial
echo 01 > serial

echo 'create file index.txt'
echo '---------------------------------------------------------------------'

if [ -f ./index.txt ];
then
	rm ./index.*
fi;

touch index.txt
touch index.txt.attr

