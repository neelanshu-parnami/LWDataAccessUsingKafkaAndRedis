About :-
This repository contains sample code for live creating/updating data to Redis Cache, using Kafka and Zookeper.
This approach provides benefit of proving faster data access using Redis Cache (In-memory data) instead of costly database access ensuring :- 
1. Live data available in cache (Always recent)
2. Auto Updating of Redis at the instant data is written (created/updated) to Database

Pre-requisite :-
- Apache Kafka (Kafka Version - 1.0.0, Scala Version - 2.11)
- Zookeeper (Version - 3.4.11)
- Postgres (Version - 9.6)
- Redis (Version - 3.2.100)
- Java 8
- Lombok (For automatic getters/setters - Required to compile the source code)
- STS (Required to run as Spring Boot Application)
- Code Editor (Any editor which supports STS plugin such as Eclipse/Netbeans/Intellij)
- Postman (Version - v5.3.2) - To run application API's

Optional
- Redis Desktop Manager (Version - 0.8.8.384) - To view Redis changes
- PgAdmin (Version - pgAdmin-4) - To view postgres databases changes

Code Explained :- 
Assumption :- Request Url assuming applications runing on localhost 8080.
1. Database Interaction REST Module :-
   a) Overview - Allows end user to write data(Insert/Update) to Database.
   b) API Requests 
		i)   Insert Data :-		
			 Request Type :- POST
			 Request Url :- http://localhost:8080/rest/article 
			 Headers :- Accept : application/json, Content-Type : application/json
			 Body :- {"title":"Article title","category":"Article Category"}
			 Output :- Inserts new article in database, produces a kafka message for same containing created database entry, which can be consumed readily and written to Redis if Consumer is running (We can run consumer indefnately for live data insert in Redis)
		ii)  Update Data :- 
			 Request Type :- PUT
			 Request Url :- http://localhost:8080/rest/article
			 Headers :- Accept : application/json, Content-Type : application/json
			 Body :- {"articleId": 6 (id of article to update in integer), "title":"Article 6 title modified","category":"Article 6 category modified"}
			 Output :- Updates existing article having id=6 in database, produces a kafka message for same containing updated database entry, which can be consumed readily and updated in Redis if Consumer is running (We can run consumer indefnately for live data update in Redis)
		iii) Read Data :-
			 Request Type :- GET
			 Request Url :- http://localhost:8080/rest/article/<article_id>
			 Headers :- Content-Type : application/json
			 Example request :- http://localhost:8080/rest/article/6
			 Output :- Gets the data for specified article id from database :- 
			 {"articleId": 6 (id of article to update in integer), "title":"Article 6 title modified","category":"Article 6 category modified"}
   c) Code Ref :- 
		Main Class/Application Startup File :- com.metacube.poc.kafkaredispoc.KafkaRedisPocApplication.java

2. Consumer Module :- 
   a) Overview - Consumes message from Kafka and inserts/updates it to Redis (For most recent data available to Redis).
   b) Application Type :- Java application
   c) Code Ref :- 
		Main Class/Application Startup File :- com.metacube.poc.kafkaredispoc.kafka.KafkaConsumerService.java	

3. Redis Interaction REST Module :-
   a) Overview - Allows end user to read from Redis cache directly.
   b) API Requests 
		i) Read Data :-
			 Request Type :- GET
			 Request Url :- http://localhost:8080/redis/article/<article_id>
			 Headers :- Content-Type : application/json
			 Example request :- http://localhost:8080/redis/article/6
			 Output :- Gets the data for specified article id from Redis cache :- 
			 {"articleId": 6 (id of article to update in integer), "title":"Article 6 title modified","category":"Article 6 category modified"}		
   c) Code Ref :- 
		Main Class/Application Startup File :- Same as "Database Interaction REST Module" i.e. com.metacube.poc.kafkaredispoc.KafkaRedisPocApplication.java

Future Work/Extensibilty :- 
1. Use advance features of Kafka, such as Streaming API, Connect API for optimized/powerful solution. 
Try using Debezium Connector for automatically capturing Database changes, References :- 
Documentation :- http://debezium.io/docs/connectors/postgresql/
Reuse Docker Images :- Download code from :- https://github.com/debezium/docker-images/ and create image in Docker Terminal (https://docs.docker.com/toolbox/toolbox_install_windows/ ) by running build-all.sh in downloaded debezium code
2. Create a single Docker image for required technology pool and distribute as a reusable solution.

Reference to solution/idea  :- 
Lightweight Data Access Technique.pptx (Can found in current directory)
		
Important links :- 
1. Install Kafka and Zookeeper (https://medium.com/@shaaslam/installing-apache-kafka-on-windows-495f6f2fd3c8)
2. Install Redis (https://redis.io/download)
3. Installing Redis Desktop Manager - http://docs.redisdesktop.com/en/latest/install/#windows
4. lombok - https://projectlombok.org/   (Install in eclipse)




