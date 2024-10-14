package com.azhar.taskmanagement.dao;

import jakarta.persistence.*;
import lombok.Data;

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
    private String headers;

    @Column(columnDefinition = "jsonb")
    private String queryParams;

    @Column(columnDefinition = "jsonb")
    private String requestBody;

    private LocalDateTime timestamp;
}
