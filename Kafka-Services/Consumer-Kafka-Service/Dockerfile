
FROM docker.io/library/eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/consumer-kafka-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/app/app.jar"]