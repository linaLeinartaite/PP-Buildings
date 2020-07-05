# Buildings

# Short description
This is a maven project for Real Estate Building Registry. 
Project is written using Spring Boot (version 2.3.1),  
JUnit (5.6.2) & Mockito (included in springframework) for tests,
Swagger (2.9.2) for documentation,
in-memory database H2 for saving data:

    url=jdbc:h2:file:C:/data/test	
    username=root
    password=root
To reach database you need to start aplication using methods described bellow. Then open the link http://localhost:8080/h2-console using browser of your chose.

# Prerequisites
Maven - Dependency Management,
Java SE Development Kit 11, or latter version.
Download and unpack the recent version of Maven (https://maven.apache.org/download.cgi) and add maven path to environment variables. Step by step instruction can be found on Maven site (https://maven.apache.org/install.html).
You can run this code by using an IDE like NetBeans, Intellij IDEA, or Eclipse.
You also can run using Maven or package and run using Java.
To run using maven, stand in project top directory: 

    mvn clean install 
    mvn spring-boot:run
To run using java, stand in project top directory:

    mvn package
    java -jar target/buildings-0.0.1-SNAPSHOT.jar
Creating package is also useful for deployments onto server. The only requirement for server is to have java installed.

# API
Detail description about API is documented using Swagger.
To see documentation You need to start application by using one of the methods above. Then open the link http://localhost:8080/swagger-ui.html using browser of your chose.

# Authors
Lina Leinartaitė
