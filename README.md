# Trending Meetup locations
This application has backend and fronend implementation which can retrieve, process and display the most popular meetup locations in the form of heatmap. It consists of 4 projects:

1. **collection-kafka:** It is a Springboot application and has basically two purpose.
	* Reads the live input stream from RSVP meetup.com. 
	https://stream.meetup.com/2/rsvps
	* Send it continuously to a Kafka topic (meetuprsvp) so that the data can be processed as streams using Apache Spark.
	
2. **spark-kafka-analyzer:** It is a Springboot application and serves the following purposes.
	* Reads the message from the same Kafka topic (meetuprsvp) and processes it to find the venue count as streams of batches using Apache Spark.
	* Updates the venue details along-with the calculated count in the cassandra database (keyspace(rsvp) & table(meetupfrequenncy)) and if the venue is already present, it just updates the count.
	
3. **meetup-reactive-service:** It is reactive Spring boot application and serves the following purposes:
	* It used the Cassandra DB reactive driver to produce flux data from the same Cassandra keyspace (rsvp) and table (meetupfrequenncy).
	* It will expose the analyzed Venue Frequency as a rest end point (/meetupVenues).
	
4. **meetup-map:** This is an Angular application which shows the heatmap. It has following features:
	* It uses AGM (Angular Google maps) libarary to show the heatmap coordinates along-with weight.
	* It uses a Server side event (SSE) support to the above endpoint (/meetupVenues) and plots the heatmap overlay.

### Technical Requirements
For building and running the application you need:

1. JDK 1.8x
2. Maven 3.x.x
3. Tomcat 9.x (if not using integrated tomcat)
4. Kafka setup & running (Single node)
5. Apache Cassandra 3.xx.xx
6. Apache Spark 3.x.x

### Prerequisites

1. **Kafka setup:**

	Start Zookeeper and Kafka Broker server. Although, default configuration is already present, make use you update the configurations in applications to connect this instance.
	Create a topic named ‘meetuprsvp’ on your kafka cluster.
	kafka-topics.bat(or, /sh for linux) --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic meetuprsvp
	
2. **Cassandra setup:**

	Make sure Cassandra instance is running in your machine. Create the keyspace named ‘rsvp’ and then, create table named ‘meetupfrequenncy’ within that keyspace.
	
	CREATE KEYSPACE IF NOT EXISTS rsvp
		WITH REPLICATION = {
		'class' : 'SimpleStrategy',
		'replication_factor' : 1
	};
	
	USE rsvp;

	CREATE TABLE meetupfrequenncy (
		venue_id int, 
		venue_name text, 
		lat double, 
		lon double, 
		count counter,
		PRIMARY KEY ((venue_id, venue_name), lat, lon)
	);
	

### Steps to run collection-kafka, spark-kafka-analyzer & meetup-reactive-service:
1.  Verify required configuration for kafka/Cassandra in application.yml.

2.	Run maven command to install dependency.
	
	mvn clean install

3.	Compile and run the project.

	mvn spring-boot:run
	
## Code Analysis
SonarQube has been used as static code analyzer for code analysis (i.e. for bugs/vulnerabilities/Code smells).

#### Developed by [Ajitav Chatterjee](https://github.com/ajitavchatterjee)

##### DISCLAIMER: Developed as a Dummy project for Proof of Concept only. It has got nothing to do with any proprietary codes/resources.
