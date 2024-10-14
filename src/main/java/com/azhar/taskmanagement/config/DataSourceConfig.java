package com.azhar.taskmanagement.config;

import com.azhar.taskmanagement.aws.SecretManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private SecretManagerService secretManagerService;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource dataSource() {
        Map<String, String> secret = secretManagerService.getSecret("dev/app/pgsql");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName()); // Example for MySQL
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setPassword(secret.get("password"));
        dataSource.setUsername(secret.get("username")); // Replace with your DB username
        return dataSource;
    }
}
