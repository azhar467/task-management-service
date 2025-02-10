package com.azhar.taskmanagement.config;

import com.azhar.taskmanagement.aws.SecretManagerService;
import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@Profile("!local")
public class DataSourceConfig {

    @Autowired
    private final SecretManagerService secretManagerService;

    @Autowired
    private final DataSourceProperties dataSourceProperties;

    private final String databaseSecretName;

    public DataSourceConfig(SecretManagerService secretManagerService, DataSourceProperties dataSourceProperties) {
        this.secretManagerService = secretManagerService;
        this.dataSourceProperties = dataSourceProperties;
        Dotenv dotenv = Dotenv.load();
        this.databaseSecretName = dotenv.get("DATABASE_SECRET_NAME");
    }

    @Bean
    public DataSource dataSource() {
        Map<String, String> secret = secretManagerService.getSecret(databaseSecretName);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setSchema(dataSourceProperties.getSchema());
        dataSource.setUsername(secret.get("username"));
        dataSource.setPassword(secret.get("password"));
        return dataSource;
    }

    @Bean
    public Flyway flyway() {
        Map<String, String> secret = secretManagerService.getSecret(databaseSecretName);
        Flyway flyway = Flyway.configure()
                .dataSource(dataSourceProperties.getUrl(), secret.get("username"), secret.get("password"))
                .locations("classpath:db/migration").schemas(dataSourceProperties.getSchema())  // Ensure the migration location is correct
                .load();
        flyway.migrate();  // Make sure Flyway is instructed to run the migrations
        return flyway;
    }
}
