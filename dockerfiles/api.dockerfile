#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as build

# Set the current working directory inside the image
WORKDIR /code

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

RUN dos2unix mvnw

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline. 
# This is a separate step so the dependencies will be cached unless 
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests \
    && cp target/*.jar ./app.jar


#### Stage 2: A minimal docker image with command to run the app 
FROM openjdk:8-jre-alpine

# Copy project from the build stage
COPY --from=build /code/target/*.jar ./app.jar

CMD [ "java", "-jar", "app.jar" ]
