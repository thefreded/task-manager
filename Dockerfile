FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from your build to the container
COPY target/task-manager-app.jar /app/task-manager-app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "task-manager-app.jar"]
