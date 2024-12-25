#FROM openjdk:11
#COPY . /usr/src/myapp
#WORKDIR /usr/src/myapp/src/main/java/com/example/Task/Maneger
#RUN javac TaskManegerApplication.java
#CMD ["java", "TaskManegerApplication"]
#

FROM openjdk:11

# Copy the whole project to the container's directory
COPY . /usr/src/myapp

# Set the working directory to where your Java source files are located
WORKDIR /usr/src/myapp/Task-Manager/src/main/java/com/example/Task/Maneger

# List the files in the working directory (for debugging purposes)
RUN ls -alh

# Compile the Java code
RUN javac TaskManegerApplication.java

# Run the Java application
CMD ["java", "TaskManegerApplication"]
