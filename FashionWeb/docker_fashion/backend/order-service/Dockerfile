FROM openjdk:8-jdk-alpine
ARG JAR_FILE=order-service.jar
COPY ${JAR_FILE} order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]
