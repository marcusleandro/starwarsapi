# Star Wars API
This project is a challenge from B2W to implement a API using REST to manage data from planets according to [swapi](https://swapi.co/).

Here you will find a Start Wars API project using Java, Spring Boot and MongoDB for this solution purpose.

## Requirements:

- The API must be REST;

- For each planet, the following data must be obtained from the application database and entered manually:
	- name
	- climate
	- terrain

- For each planet we must also have the number of movie appearances that can be obtained through the public Star Wars API: https://swapi.co/

## Desired Features:

- Add a planet (with name, climate e terrain);
- List Planets;
- Search by name;
- Search by Id;
- Remove a planet;

## Description:

Since this is java maven spring boot project, you need the JVM installed in your machine in order to run. You also need to provide a Mongo database up and running to the application since it is a REST API that do reads and writes in a Mongo database that can be setted in [application.properties](src/main/resources/application.properties) file by changing the value of the `spring.data.mongodb.uri` and `spring.data.mongodb.database` properties. The defaults for these values are `mongodb://localhost:27017` and `starwars` respectively, but you can change if needed.

Also there is a [application-test.properties](src/main/resources/application-test.properties) file that can be changed in order to run the tests in the case that you want to use a different database in your tests. To run the tests just use the command `mvn clean verify` and see the output for tests.

To run this project there is a Boot.java class that there is a main method, just run it or use the maven command: `mvn clean compile spring-boot:run` and see the results in [http://localhost:8080/](http://localhost:8080/)

In order to package the application run the command `mvn clean package` and see the output file `starwarsapi-1.0.0.jar` in the target folder. To run the api with the jar generated just use the command `java -jar starwarsapi-1.0.0.jar`

## Usage:

### Adding a Planet:

Do a POST at /planets with a body in json form:
	{
		name: "Name XYZ",
		climate: "Temperate",
		terrain: "Rain Forest",
	}


### List Planets:

Do a GET at /planets and see all planets in the database.

### Search a planet By Name:

Do a GET at /planets?name=name_to_search, don't forget to pass the name parameter to find by name.

### Search by Id:

Do a GET at /planets/{id}, where {id} is the id of the planet to be searched.

### Remove a planet

Do a DELETE at /planets/{id}, where {id} is the id of the planet to be deleted.


## Obs

there is a documentation provided by Swagger at  `/swagger-ui.html`.




