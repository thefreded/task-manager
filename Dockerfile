# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file
COPY target/task-manager-app.jar /app/task-manager-app.jar


EXPOSE 8081

# Command to run the JAR file
CMD ["java", "-jar", "/app/task-manager-app.jar"]


