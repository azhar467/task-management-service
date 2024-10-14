# Use an official Maven image with OpenJDK 17 as a base for the build stage
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the src directory
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/task-management-service-0.0.1-SNAPSHOT.jar task-management-service.jar

# Expose the port your application will run on
EXPOSE 8080

# Set the image name
LABEL name="task-management-service"

# Run the application
ENTRYPOINT ["java", "-jar", "task-management-service.jar"]
