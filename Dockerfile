

# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /Task_Manager

# Copy the application's JAR file into the container
COPY target/Task-Maneger-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
