#!/bin/bash

javac -cp junit-4.11.jar:mockobjects-0.6-core.jar:mockobjects-0.6-j1.4-j2ee1.2.jar:selenium-server-standalone-2.11.0.jar:guava-10.0.1.jar:. TestingLabConverterServlet.java CityTemperatureServiceProvider.java TestTestingLabConverterServlet.java
java  -cp junit-4.11.jar:mockobjects-0.6-core.jar:mockobjects-0.6-j1.4-j2ee1.2.jar:selenium-server-standalone-2.11.0.jar:guava-10.0.1.jar:. org.junit.runner.JUnitCore TestTestingLabConverterServlet
