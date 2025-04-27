# Stage 1: Build the app
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the app as uber-jar
RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built runner jar
COPY --from=build /app/target/*-runner.jar /app/app.jar

# Run the jar
CMD ["java", "-jar", "/app/app.jar"]
