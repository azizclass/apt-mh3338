#!/bin/bash

javac -cp selenium-server-standalone-2.48.2.jar:. GoogleSuggest.java
java -cp selenium-server-standalone-2.48.2.jar:. GoogleSuggest
