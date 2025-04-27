# Stage 1: Build the app
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# First copy only pom.xml
COPY pom.xml .

# Pre-fetch dependencies (cache optimization)
RUN mvn dependency:go-offline

# Now copy the rest of the source code
COPY src ./src

# Build the JAR
RUN mvn package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*-runner.jar /app/task-manager-app.jar

CMD ["java", "-jar", "/app/task-manager-app.jar"]
