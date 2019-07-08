-----------------------------------------------------------------------------------------------         
SSL Web scripts
-----------------

SSL Web scripts is a suite of scripts for generation X.509 keys/certificates for Web Applications.

SSL Web scripts ® Copyright © 2018-2019 by SManSoft

Overview
--------
    
    SSL Web scripts generates X.509 keys/certificates, using as RSA as ECDSA.

    The main features of SSL Web scripts:
    
        - generation of CA ECDSA key and Self-Signed CA ECDSA Certificate;
        - generation of SSL ECDSA private key, ECDSA public key and sign ECDSA public key, using ECDSA CA key 
            and generation of X.509 certificate of server (server certificate);
        - generation of CA RSA key and Self-Signed CA RSA Certificate;
        - generation of SSL RSA private key, RSA public key and sign RSA public key, using RSA CA key 
            and generation of X.509 certificate of server (server certificate);
        - usage (by default) of Elliptic Curves (ECDSA-WITH-SHA256) prime256v1 (NIST P-256, secp256r1) or
            RSA2048-WITH-SHA256 for signing of public keys;

Usage
--------

    Suite of scripts generates/uses follow files (extensions):
        .key        - private key (ECDSA or RSA) in PEM format;
        .pub        - non-signed public key (ECDSA or RSA) in PEM format;
        .csr        - certificate request (for signing) of public key;
        .cert       - signed public key (ECDSA or RSA) (certificate) with additional text info in PEM format;
        .pem        - signed public key (ECDSA or RSA) (certificate) in PEM format;
        .chain.pem  - file, that contains list of authorized certificate(s), including root CA; 
        .p12        - archive (in PKCS12 format), that contains private key, public key (certificate), and authorised certificate(s);

    Suite of scripts contains follow directories:
        ssl.ecdsa.ca
        ssl.rsa.ca      - suite of scripts (using ECDSA/RSA), that:
                            - generates CA keys (private and public);
                            - generates CA Certificate Request;
                            - self-signs of CA Certificate Request;
                            - generates Server keys (private and public);
                            - generates Server Certificate Request (for signing, by CA key);
                            - signs of Server Certificate Request, by CA key;
                            - imports of signing of Server Certificate Request, by CA key;
                        
                        - files/dirs:
                            cnfs/openssl.ca.int.cnf     - configuration file, that is used for generation of CA key pair and  
                                                            self-signing of CA Certificate Request;
                            cnfs/openssl.ca.srv.cnf     - configuration file, that is used for signing of Server Certificate Request;
                            cnfs/openssl.srv.cnf        - configuration file, that is used for generation of Server key pair and generation 
                                                            of Server Certificate Request;
                            
                            out                         - directory, that contains all generated keys, certificates and .p12 (PKCS12 archive);
                            
                            ssl.ecdsa.ca.ini            - base ini file, that is used by ECDSA scripts; 
                            ssl.rsa.ca.ini              - base ini file, that is used by RSA scripts; 
                            
                            01.init.sh                  - bash script, that clears all dirs and removes all keys and certificates;
                            02.ca.sh                    - bash script, that creates CA key pair, generates CA Certificate Request
                                                            and self-signs of CA Certificate Request;
                            03.srv.sh                   - bash script, that creates Server key pair, generates Server Certificate Request, 
                                                            signs Server Certificate Request by CA key and exports Server key and certificates
                                                            to PKCS12 (.p12) file;
        
        ssl.ecdsa.req
        ssl.rsa.req     - suite of scripts (using ECDSA/RSA), that:
                            - generates Server keys (private and public);
                            - generates Server Certificate Request (for signing, by CA key);
                            - signs of Server Certificate Request, by CA key;
                            
                        - files/dirs:
                            cnfs/openssl.srv.cnf        - configuration file, that is used for generation of Server key pair and generation 
                                                            of Server Certificate Request;
                            
                            out                         - directory, that contains of Server key, certificates and .p12;
                            
                            ssl.ecdsa.req.ini            - base ini file, that is used by ECDSA scripts; 
                            ssl.rsa.req.ini              - base ini file, that is used by RSA scripts; 
                            
                            01.init.sh                  - bash script, that clears all dirs and removes all keys and certificates;
                            02.srv.req.sh               - bash script, that Server key pair and generates Server Certificate Request;
                            03.srv.imp.sh               - bash script, that exports Server key, signed certificate and CA certificates
                                                            to PKCS12 (.p12) file;
        
        ssl.ecdsa.sign
        ssl.rsa.sign    - suite of scripts (using ECDSA/RSA), that:
                            - generates CA keys (private and public);
                            - generates CA Certificate Request;
                            - self-signes of CA Certificate Request;
                            - signes of Server Certificate Request, by CA key;
                            
                        - files/dirs:
                            cnfs/openssl.ca.int.cnf     - configuration file, that is used for generation of CA key pair and  
                                                            self-signing of CA Certificate Request;
                            cnfs/openssl.ca.srv.cnf     - configuration file, that is used for signing of Server Certificate Request;
                            
                            out                         - directory, that contains CA key and certificates;
                            
                            ssl.ecdsa.sign.ini          - base ini file, that is used by ECDSA scripts; 
                            ssl.rsa.sign.ini            - base ini file, that is used by RSA scripts; 
        
                            01.init.sh                  - bash script, that clears all dirs and removes all keys and certificates;
                            02.ca.sh                    - bash script, that creates CA key pair, generates CA Certificate Request
                                                            and self-signs of CA Certificate Request;
                            03.srv.sign.sh              - bash script, that signs Server Certificate Request by CA key;
                            
        If you use ssl.ecdsa.req/ssl.rsa.req and ssl.ecdsa.sign/ssl.rsa.sign together, you should:
                        1.  call:
                                ssl.ecdsa.sign/02.ca.sh
                                    or
                                ssl.rsa.sign/02.ca.sh
                        2.  call:
                                ssl.ecdsa.req/02.srv.req.sh
                                    or
                                ssl.rsa.req/02.srv.req.sh
                        3.  copy:
                                ssl.ecdsa.req/out/srv.sl.csr
                                    or 
                                ssl.rsa.req/out/srv.sl.csr
                        4.  call:
                                ssl.ecdsa.sign/03.srv.sign.sh
                                    or
                                ssl.rsa.sign/03.srv.sign.sh
                        5.  copy:
                                    ssl.ecdsa.sign/out/srv.sl.cert
                                        or
                                    ssl.rsa.sign/out/srv.sl.cert
                                and
                                    ssl.ecdsa.sign/out/ca.sl.cert
                                        or
                                    ssl.rsa.sign/out/ca.sl.cert 
                                to
                                    ssl.ecdsa.req/out
                                        or
                                    ssl.rsa.req/out
                        6.  call:
                                ssl.ecdsa.req/03.srv.imp.sh
                                    or
                                ssl.rsa.req/03.srv.imp.sh
                                
        You can use result files:
            ca.sl.cert/ca.sl.pem 
            srv.sl.cert/srv.sl.pem
            srv.sl.key
                or
            srv.sl.p12
        in Web applications.
        
        Files openssl.xxx.srv.cnf contain section:
            [alt_names]
            DNS.1=localhost
        You can change domain name to some other or add some new domain name(s).
        
        In practice, you should generate Server key pair, generate Certificate request, sign Server certificate request,
            using official Authorized Centers and then import results to srv.sl.p12.

Please, send your notes and questions to
    mailto:info@smansoft.com
.

-----------------------------------------------------------------------------------------------         
End of document
-----------------       
