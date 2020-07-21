# collection-kafka
This has basically two purpose:
1. Reads the live input data from RSVP meetup.com data. 
	https://stream.meetup.com/2/rsvps
2. Send it continuously to a Kafka topic so that the data can be processed as streams using Apache Spark.

## Requirements
For building and running the application you need:

1. JDK 1.8x
2. Maven 3.x.x
3. Tomcat 9.x (if not using integrated tomcat)
4. Kafka setup & running (Single node)

## Steps to run the application:
1.  Create a kafka topic named "meetuprsvp"

2.  Change required configuration for kafka in application.yml. Although, default configuration is already present.

2.	Run maven command to install dependency.
	
	mvn clean install

2.	Compile and run the project.

	mvn spring-boot:run
	
## Code Analysis
SonarQube 7.9.1 has been used as static code analyzer for code analysis (i.e. for bugs/vulnerabilities/Code smells).

#### Developed by [Ajitav Chatterjee](https://github.com/ajitavchatterjee)

##### DISCLAIMER: Developed as a Dummy project for Proof of Concept only. It has got nothing to do with any proprietary codes/resources.
