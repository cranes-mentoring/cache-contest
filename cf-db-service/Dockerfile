FROM openjdk:22-jdk-slim

WORKDIR /app

COPY build/libs/cf-db-service-0.1.1.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]