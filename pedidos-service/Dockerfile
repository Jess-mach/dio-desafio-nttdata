FROM eclipse-temurin:21-jdk-alpine

VOLUME /tmp
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8200

ENTRYPOINT ["java", "-jar", "app.jar"]
