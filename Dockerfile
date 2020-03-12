FROM openjdk:11
ADD ./build/libs/websockets-1.0.jar /var/websockets-1.0.jar
ADD ./src/main/resources/application-docker.properties /var/application.properties
ENTRYPOINT java -jar /var/websockets-1.0.jar --spring.config.location=/var/application.properties