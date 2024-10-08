# Use an official Maven image with OpenJDK 17 as a base
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Copy the src directory
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar task-management-service.jar

# Expose the port your application will run on
EXPOSE 8080

# Set the image name
LABEL name="task-management-service"

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]