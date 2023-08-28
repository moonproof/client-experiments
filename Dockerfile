FROM openjdk:17-jdk-slim

MAINTAINER moonproof
COPY ./build/libs/client-experements.jar experiments.jar
ENTRYPOINT ["java","-jar","/experiments.jar"]