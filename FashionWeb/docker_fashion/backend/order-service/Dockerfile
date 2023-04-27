FROM openjdk:8-jdk-alpine
ARG JAR_FILE=order-service-0.0.1.jar
COPY ${JAR_FILE} order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]
