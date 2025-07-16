package com.azhar.taskmanagement.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log", schema = "task_management_service_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "request_method")
    private String requestMethod;
    
    @Column(name = "request_uri")
    private String requestUri;
    
    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;
    
    @Column(name = "response_payload", columnDefinition = "TEXT")
    private String responsePayload;
    
    @Column(name = "response_status")
    private Integer responseStatus;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "execution_time_ms")
    private Long executionTimeMs;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}