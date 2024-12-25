# Install Maven
FROM maven:3.8.6-openjdk-11 as build

# Set the working directory
WORKDIR /usr/src/myapp

# Copy the project files and run Maven to build the project
COPY . /usr/src/myapp
#RUN mvn clean package

# Run the JAR file (assuming you have a JAR file named task-manager.jar)
CMD ["java", "-jar", "/usr/src/myapp/target/task-manager-0.0.1-SNAPSHOT.jar"]
