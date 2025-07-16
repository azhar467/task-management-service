package com.azhar.taskmanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class DatabaseResetConfig {
    
    @Value("${app.database.reset-on-startup:true}")
    private boolean resetOnStartup;
    
    private final DataSource dataSource;
    
    public DatabaseResetConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void resetDatabase() {
        if (resetOnStartup) {
            log.info("Database reset flag is enabled. Cleaning and migrating database...");
            try {
                Flyway flyway = Flyway.configure()
                        .dataSource(dataSource)
                        .locations("classpath:db/migration")
                        .load();
                
                flyway.clean();
                flyway.migrate();
                
                log.info("Database reset completed successfully");
            } catch (Exception e) {
                log.error("Failed to reset database: {}", e.getMessage());
            }
        } else {
            log.info("Database reset is disabled. Skipping clean operation.");
        }
    }
}