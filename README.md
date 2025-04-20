# Keycloak with Spring Boot DemoApp
App made with Spring Boot (Java), Keycloak and MySQL.  

This application allows:
- Authorize restful GET HTTP services using plain username and password (By right should use POST, GET HTTP for demo purposes)
- restful GET HTTP to get a distance between 2 postcodes
- restful POST HTTP to save a postcode, latitude and longitude 

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
#### backend:  
`SocialAppApiApplication: Started SocialAppApiApplication in 42.05 seconds (JVM running for 69.204)`
`✔ Compiled successfully.`
#### mysql:  
`/usr/sbin/mysqld: ready for connections. Version: '8.0.30'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.`
#### keycloak:  
`Running the server in development mode. DO NOT use this configuration in production.`  

*Note: You can see the log of each container clicking on their names on Docker Desktop.*

*Note 2: To deploy the application you need the following ports to be free on your PC: 3036, 4200, 8080 and 8090.*

When all the containers are built, you will be able to open the application by putting `http://localhost:4200` in your browser.
## MVN JUnit Testing
`CD spring-boot-keycloak-mysql\Backend`
`mvn clean install`

http://localhost:8090/wcc/distance/get?source=50088&dest=70000
{
    "unit": "KM",
    "distance": 53.91553527963154
}

## Tech Stack

**Server:** Java, Spring Boot, Hibernate

**Authorization server:** Keycloak

**Database:** MySQL

## Postman Screen Shot
<b>HTTP GET login</b>
<p align="center">
  <img width="1200" height="700" src="/pic/login.png">
</p>

<b>HTTP GET distance</b>
<p align="center">
  <img width="460" height="300" src="/pic/distance.png">
</p>

<b>HTTP POST Save Postcode</b>
<p align="center">
  <img width="460" height="300" src="/pic/save.png">
</p>