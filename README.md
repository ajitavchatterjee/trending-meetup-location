# Trending Meetup locations
This application has backend and fronend implementation which can retrieve, process and display the most popular meetup locations in the form of heatmap. It consists of 4 projects:

1. **collection-kafka:** It is a Springboot application and has basically two purpose.
	* Reads the live input stream from RSVP meetup.com. 
	https://stream.meetup.com/2/rsvps
	* Send it continuously to a Kafka topic (meetuprsvp) so that the data can be processed as streams using Apache Spark.
	
2. **spark-kafka-analyzer:** It is a Springboot application and serves the following purposes.
	* Reads the message from the same Kafka topic (meetuprsvp) and processes it as streams of batches using Apache Spark to find the venue count.
	* Increment the venue count with the calculated count in the cassandra database (keyspace: rsvp & table: venuefrequency) and if the venue is new, it simply just creates a new entry in the table.
	
3. **meetup-reactive-service:** It is a reactive Spring boot application and serves the following purposes:
	* It uses the Cassandra DB reactive driver to produce flux data from the same Cassandra table (venuefrequency) inside keyspace (rsvp).
	* It will expose the analyzed Venue Frequency data as a REST endpoint (/meetupVenues).
	
4. **meetup-map:** This is an Angular application which shows the heatmap. It has following features:
	* It uses AGM (Angular Google maps) libarary to show the heatmap coordinates along-with weight.
	* It uses a Server side event (SSE) support to call the above endpoint (/meetupVenues) and plots the heatmap overlay.

### Technical Requirements
For building and running the application you need.

**Backend**
1. JDK 1.8x
2. Maven 3.x.x
3. Tomcat 9.x (if not using integrated tomcat)
4. Kafka setup & running (Single node)
5. Apache Cassandra 3.xx.xx
6. Apache Spark 3.x.x


**Frontend**
1. Node version v10.16.0
2. npm version 6.9.0
3. Angular CLI version 8.3.5

### Prerequisites

1. **Kafka setup:**

	Start Zookeeper and Kafka Broker server. Although, default configuration is already present, make use you update the configurations in applications to connect this instance.
	
	Create a topic named ‘meetuprsvp’ on your kafka cluster.
	
	kafka-topics.bat(or, /sh for linux) --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic meetuprsvp
	
2. **Cassandra setup:**

	Make sure Cassandra instance is running in your machine. Create the keyspace named ‘rsvp’ and then, create table named ‘venuefrequency’ within that keyspace.
	
	CREATE KEYSPACE IF NOT EXISTS rsvp
		WITH REPLICATION = {
		'class' : 'SimpleStrategy',
		'replication_factor' : 1
	};
	
	USE rsvp;

	CREATE TABLE venuefrequency (
		venueId int, 
		venueName text, 
		lat double, 
		lon double, 
		count counter,
		PRIMARY KEY ((venueId, venueName), lat, lon)
	);
	
3. **Google Maps seup:**

	Go to the google maps console of your account and generate a non-restricted API key.
	Follow the link for the steps:
	https://developers.google.com/maps/documentation/javascript/get-api-key
	
	Now, go to the Angular frontend application and set the value ‘googleMapsAPIKey’ under environments with the generated API key.
	Path: trending-meetup-location -> meetup-map -> src -> environments -> environment.ts
	

### Steps to run backend applications: collection-kafka, spark-kafka-analyzer & meetup-reactive-service:
1.  Verify required configuration for kafka/Cassandra/server port in application.yml.

2.	Run maven command to install dependency.
	
	mvn clean install

3.	Compile and run the project.

	mvn spring-boot:run
	
	
### Steps to run fronend application: meetup-map:

1. Open command prompt, navigate to application folder and run below command to install all the dependencies

    npm install

2. Run the below command to start the application

    ng serve -o

3. After the server starts, the application will get open in below URL in browser to show the heatmap:

    http://localhost:4200
	
## Code Analysis
SonarQube has been used as static code analyzer for code analysis (i.e. for bugs/vulnerabilities/Code smells).

#### Developed by [Ajitav Chatterjee](https://github.com/ajitavchatterjee)

##### DISCLAIMER: Developed as a Dummy project for Proof of Concept only. It has got nothing to do with any proprietary codes/resources.
