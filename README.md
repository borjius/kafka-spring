## This Project

This is a POC for kafka + spring + avro. In a spring boot application, with a docker kafka, we will publish events with avro ser/des and consume them.


##To test this project:

- Compile:
mvn clean install

- Run docker:
docker-compose up

- Run App:
mvn spring-boot:run

- Call Endpoint for easy publishing:
curl http://localhost:8080/orders/{anyStringOrderId}
