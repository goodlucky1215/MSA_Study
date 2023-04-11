FROM openjdk:8-jdk-alpine
ARG JAR_FILE=fashion-eureka.jar
COPY ${JAR_FILE}  eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]
