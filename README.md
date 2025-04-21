# Keycloak with Spring Boot DemoApp
App made with Spring Boot (Java), Keycloak and MySQL.  

This application allows:
- Authorize restful GET HTTP services using plain username and password (By right should use POST, GET HTTP for demo purposes)
- Restful GET HTTP to get a distance between 2 postcodes
- Restful POST HTTP to save a postcode, latitude and longitude 

***Note:** this is a personal practice project and I do not allow its distribution.*  
## API Docs
To view the API documentation you must first [deploy](#deployment) the application and then enter one of the following paths in your browser:
- http://localhost:8090/swagger-ui.html (To view the docs with a graphical interface)
## Deployment
***Important note:** At the moment the app starts in developer mode and is not adapted for production.*  

The application can be deployed using a single command thanks to Docker.  
First of all, download [Docker Desktop](https://www.docker.com/products/docker-desktop/) if you don't have it installed locally. 

Then you have to clone this repository:  
`git clone https://github.com/pcyuen98/spring-boot-keycloak-mysql`  
or download it as a ZIP file.

After that, you will be able to deploy this app running the following command in
the repository folder:  
`docker compose up`  
You will need to have Docker Desktop open and running when you execute this command (and any other Docker command).


The first time you run the application it will be slow because Docker needs to download and build the images specified in the `docker-compose.yml` file.  
You will be able to know when the project is ready when all the containers compile correctly. You will see the next lines in log of each container:

#### mysql:  
`/usr/sbin/mysqld: ready for connections. Version: '8.0.30'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.`
#### keycloak:  
`Running the server in development mode. DO NOT use this configuration in production.`  

*Note: You can see the log of each container clicking on their names on Docker Desktop.*

*Note 2: To deploy the application you need the following ports to be free on your PC: 3036, 4200, 8080 and 8090.*

#### Run the backend: 
java -jar Backend\target\App_api-0.0.1-SNAPSHOT.jar <br>
`2025-04-20 14:42:11.762  INFO 41372 --- [           main] o.s.b.w.e.t.TomcatWebServer              : Tomcat started on port(s): 8090 (http) with context path ''`

When all the containers are built and backend is started, you will be able to run the mvn command for JUnit Testing
## MVN JUnit Testing

<b>JUnit Test Coverage</b> <br>
<b>1. ControllerTest</b> - Integration tests to verify the context loading and REST controller availability in the Spring Boot application. <br>
<b>2. DistanceTest</b> - Integration tests for the distance calculation and secured endpoint authorization. <br>
<b>3. LoginTest</b> - Integration test for verifying Keycloak authentication and authorization for protected Spring Boot endpoints. <br>
<b>4. PostRepositoryTest</b> - Unit tests for IPostRepository using H2 in-memory database. <br>
<b>5. RepositotyTest</b> - Integration tests for verifying Spring context loading and controller availability. <br>

<br>
<b>Run the command below</b> <br>
cd spring-boot-keycloak-mysql\Backend<br>
mvn clean install <br>

`Expected result`
`[INFO] Results:`
`[INFO]`
`[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0`
`...`
`[INFO] BUILD SUCCESS`

## Tech Stack

**Server:** Java, Spring Boot, Hibernate

**Authorization server:** Keycloak

**Database:** MySQL

## Postman Screen Shot
<b>HTTP GET login</b>
<p align="center">
  <img width="800" height="500" src="/pic/login.png">
</p>

<b>HTTP GET distance</b>
<p align="center">
  <img width="800" height="500" src="/pic/distance.png">
</p>

<b>HTTP POST Save Postcode</b>
<p align="center">
  <img width="800" height="500" src="/pic/save.png">
</p>

## Swagger Screen Shot
<p align="center">
  <img width="800" height="500" src="/pic/swagger.png">
</p>

## Special Note
1. Authentication shall use HTTP POST instead of HTTP GET as password is exposed<br>
http://localhost:8090/wcc/login?username=admin&password=adminpass<br><br>

2. LoginController can be splited into another Microservices for scalability

3. Spring Boot must be started to run mvn test as it's include integration testing with keycloak.
