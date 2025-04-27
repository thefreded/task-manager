# Build the app
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app


# Install Maven manually
RUN apt-get update && apt-get install -y maven

# Copy everything needed to build
COPY . .

# Build the jar (skip tests to speed up)
RUN ./mvnw package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar /app/task-manager-app.jar

# Run the app
CMD ["java", "-jar", "/app/task-manager-app.jar"]
