package com.azhar.taskmanagement.aws;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Service
public class SecretManagerService {

    private final SecretsManagerClient secretsManagerClient;

    Dotenv dotenv = Dotenv.load();
    String region = dotenv.get("AWS_REGION");

    public SecretManagerService() {
        assert region != null;
        this.secretsManagerClient = SecretsManagerClient.builder()
                .region(Region.of(region)) // Using Region from .env
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public Map<String, String> getSecret(String secretName) {
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
        return parseSecret(getSecretValueResponse.secretString());
    }

    private Map<String, String> parseSecret(String secretString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(secretString, Map.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing secret string", e);
        }
    }
}
