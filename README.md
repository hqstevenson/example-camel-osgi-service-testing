# example-camel-osgi-service-testing

A set of projects demonstrating some methods of testing Camel routes that are consumers/users of OSGi services.

NOTE:  All of the projects may need to be built and installed before the tests will run

mvn -DskipTests clean install

mvn test

2016-02-18 - Changed the example a little
 - removed the service registration from the service-interface bundle
 - added a camel-scr consumer - still working on getting the test to work

