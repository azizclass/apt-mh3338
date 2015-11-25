#!/bin/bash

javac -cp junit-4.11.jar:. RationalTest.java Rational.java
java -cp junit-4.11.jar:. RationalTest
java -XX:-UseSplitVerifier -cp emma.jar emmarun -sp . -r html -cp .:junit-4.11.jar \
    RationalTest
