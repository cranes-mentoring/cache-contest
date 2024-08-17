FROM openjdk:22-jdk-slim

WORKDIR /app

COPY build/libs/hz-service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]