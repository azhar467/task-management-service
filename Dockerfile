# Use an official OpenJDK 17 image as a base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container (this will be created if it doesn't exist)
WORKDIR /app

# Copy the built JAR file from the host machine to the container
COPY target/task-management-service-0.0.1-SNAPSHOT.jar /app/task-management-service.jar

# Expose the port your application will run on
EXPOSE 8080

# Set the image name
LABEL name="task-management-service"

# Run the application
ENTRYPOINT ["java", "-jar", "/app/task-management-service.jar"]
