#!/bin/bash

javac -cp junit-4.12.jar:selenium-server-standalone-2.48.2.jar:. Foo.java
java -cp junit-4.12.jar:selenium-server-standalone-2.48.2.jar:. org.junit.runner.JUnitCore Foo
