version: '3.8'

services:
postgres-db:
image: postgres:17  # Use PostgreSQL 17 image
container_name: postgres-db
environment:
POSTGRES_USER: postgres
POSTGRES_PASSWORD: root
POSTGRES_DB: task_management_service
ports:
        - "5432:5432"  # Expose PostgreSQL on port 5432
volumes:
        - postgres_data:/var/lib/postgresql/data  # Persist PostgreSQL data
networks:
        - backend

task-service:
build: .
ports:
        - "8080:8080"  # Application port
environment:
SPRING_DATASOURCE_URL
