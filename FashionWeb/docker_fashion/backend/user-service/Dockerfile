FROM openjdk:8-jdk-alpine
ARG JAR_FILE=user-service-0.0.1.jar
COPY ${JAR_FILE} user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]
