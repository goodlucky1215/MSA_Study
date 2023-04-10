FROM openjdk:8-jdk-alpine
WORKDIR JAR_FILE=
COPY ${JAR_FILE} user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]
