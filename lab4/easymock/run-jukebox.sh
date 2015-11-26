#!/bin/bash

javac -cp junit-4.12.jar:hamcrest-core-1.3.jar:easymock-3.4.jar:objenesis-tck-2.2.jar:cglib-nodep-2.2.2.jar:.  TestJukebox.java Jukebox.java
java -cp junit-4.12.jar:hamcrest-core-1.3.jar:easymock-3.4.jar:objenesis-tck-2.2.jar:cglib-nodep-2.2.2.jar:. org.junit.runner.JUnitCore TestJukebox
