# Java 8,
FROM openjdk:8-jdk-alpine

# Java 11
#FROM adoptopenjdk/openjdk11:alpine-jre

#Buscamos el nombre del JAR generado por Maven
ARG JAR_FILE=target/*.jar

WORKDIR /opt/app
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
