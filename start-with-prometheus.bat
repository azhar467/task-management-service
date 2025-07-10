@echo off
echo Starting Task Management Service with Prometheus...

REM Start Prometheus in background
start "Prometheus" prometheus --config.file=prometheus.yml --web.listen-address=:9090

REM Wait a moment for Prometheus to start
timeout /t 3 /nobreak > nul

REM Start Spring Boot application
mvn spring-boot:run

REM When Spring Boot stops, kill Prometheus
taskkill /f /im prometheus.exe > nul 2>&1