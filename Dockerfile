# Set the working directory for the build stage
WORKDIR /app

# Copy the pom.xml and the source code into the container
COPY pom.xml .
COPY src ./src

# Build the application (this will create the JAR file in the target directory)
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/task-management-service-0.0.1-SNAPSHOT.jar /app/task-management-service.jar

# Expose the port your application will run on
EXPOSE 8080

# Set the image name
LABEL name="task-management-service"

# Run the application
ENTRYPOINT ["java", "-jar", "/app/task-management-service.jar"]
