FROM openjdk:8-jdk-alpine
ARG JAR_FILE=fashion-eureka-0.0.1.jar
COPY ${JAR_FILE}  eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]
