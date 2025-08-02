# 1) build stage com Maven + OpenJDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia pom + código e executa o build
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# 2) runtime stage com OpenJDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copia o jar gerado no builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8201
ENTRYPOINT ["java", "-jar", "app.jar"]
