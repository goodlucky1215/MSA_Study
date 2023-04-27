FROM openjdk:8-jdk-alpine
ARG JAR_FILE=fashion-apigateway-0.0.1.jar
COPY ${JAR_FILE}  fashion-apigateway.jar
ENTRYPOINT ["java","-jar","/fashion-apigateway.jar"]
