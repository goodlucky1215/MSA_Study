FROM openjdk:8-jdk-alpine
ARG JAR_FILE=seller-service-0.0.1.jar
COPY ${JAR_FILE} seller-service.jar
ENTRYPOINT ["java","-jar","/seller-service.jar"]
