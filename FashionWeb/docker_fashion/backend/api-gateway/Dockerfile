FROM openjdk:8-jdk-alpine
ARG JAR_FILE=fashion-apigateway.jar
COPY ${JAR_FILE}  api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]
