# CryptoBundle

## Introduction

CryptoBundle is a Java application aiming at comparing several cryptographic algorithms, whose GUI is designed under Swing framework. The project is purely for educational and informational purposes.

The following command lines are applied to Linux/Unix systems only:

* Generate the runnable JAR file by  `javac ./src/*.java -d ./bin && cd ./bin && jar cvfe CryptoBundle.jar Main *.class && rm -f *.class`;
* Then run the newly-produced JAR file by `java -jar CryptoBundle.jar`.

### DES

The Data Encryption Standard (DES) is a symmetric-key algorithm for the encryption of digital data. Here we implemented the DES algorithm by following the description in [FIPS PUB 46-3: Data Encryption Standard](http://csrc.nist.gov/publications/fips/fips46-3/fips46-3.pdf), published by NIST on October 25th, 1999. Noticebly, NIST officially withdrawed FIPS PUB 46-3 on May 19th, 2005 and switched to AES-256 instead.

### RSA

RSA (Rivest–Shamir–Adleman) is one of the oldest public-key cryptosystems, often used to transmit shared keys for symmetric key cryptography, according to its relatively slow speed.

## Future Work

### Triple DES

### AES
