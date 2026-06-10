# Build stage
FROM maven:3.9.16-eclipse-temurin-25-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Run stage
FROM amazoncorretto:25.0.0-alpine
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]