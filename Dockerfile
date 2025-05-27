# Etapa de build
FROM maven:3.9.6-eclipse-temurin-23 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/frikadasvarias-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-Xmx2g", "-jar", "app.jar"]