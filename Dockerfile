# Stage 1: Build the application
FROM maven:3.9.1-jdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/task-management-service-0.0.1-SNAPSHOT.jar /app/task-management-service.jar
EXPOSE 8080
LABEL name="task-management-service"
ENTRYPOINT ["java", "-jar", "/app/task-management-service.jar"]
