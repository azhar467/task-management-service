package com.azhar.taskmanagement.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Task Management Service API",
        version = "1.0",
        description = "API for managing tasks, users, and projects.",
            contact = @Contact(name = "Azhar Ahmed A",email = "azhar.ahmed467@gmail.com")
    )
)
public class OpenApiConfig {
    // You can add more custom configurations here if necessary
}
