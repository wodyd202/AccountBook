FROM openjdk:8-jdk-alpine
VOLUME /tmp
ENV DATABASE_USERNAME root
ENV DATABASE_PASSWORD root
ENV DATABASE_HOST localhost
ENV DATABASE_PORT 3306
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.host=${DATABASE_HOST}", "-Dspring.datasource.port=${DATABASE_PORT}" , "-Dspring.datasource.username=${DATABASE_USERNAME}", "-Dspring.datasource.username=${DATABASE_PASSWORD}", "-jar", "app.jar"]