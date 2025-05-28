# Etapa de build
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/FrikadasVarias-0.0.1-SNAPSHOT.jar app.jar
COPY downloads/ /app/downloads/
COPY src/main/resources/static/img/mesas/ /app/img/mesas/

ENV IMAGE_FOLDER=/app/img/mesas

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-Xmx2g", "-jar", "app.jar"]