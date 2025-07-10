package com.azhar.taskmanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PrometheusAutoStart {
    
    @Value("${prometheus.path:prometheus}")
    private String prometheusPath;
    
    @Value("${prometheus.config:prometheus.yml}")
    private String prometheusConfig;
    
    @EventListener(ApplicationReadyEvent.class)
    public void startPrometheus() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                prometheusPath, 
                "--config.file=" + prometheusConfig,
                "--web.listen-address=:9090"
            );
            pb.inheritIO();
            Process process = pb.start();
            log.info("Prometheus started successfully on port 9090");
            
            // Add shutdown hook to stop Prometheus
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                process.destroy();
                log.info("Prometheus stopped");
            }));
            
        } catch (IOException e) {
            log.warn("Failed to start Prometheus automatically: {}", e.getMessage());
            log.info("Please start Prometheus manually with: {} --config.file={}", prometheusPath, prometheusConfig);
        }
    }
}