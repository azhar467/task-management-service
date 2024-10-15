package com.azhar.taskmanagement.dao;

import com.azhar.taskmanagement.requestlogging.JsonConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_logs",schema = "task_management_service_schema")
@Data
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String endpoint;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = JsonConverter.class)
    private String headers;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = JsonConverter.class)
    private String queryParams;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = JsonConverter.class)
    private String requestBody;

    private LocalDateTime timestamp;
}
