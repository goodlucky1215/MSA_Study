FROM openjdk:8-jdk-alpine
ARG JAR_FILE=fashion-apigateway.main.jar
COPY ${JAR_FILE}  fashion-apigateway.jar
ENTRYPOINT ["java","-jar","/fashion-apigateway.jar"]
