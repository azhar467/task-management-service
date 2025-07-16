package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.audit.AuditLog;
import com.azhar.taskmanagement.audit.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {
    
    private final AuditLogRepository auditLogRepository;
    
    @GetMapping
    public ResponseEntity<Page<AuditLog>> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Page<AuditLog> auditLogs = auditLogRepository.findAll(
            PageRequest.of(page, size, Sort.by("createdAt").descending())
        );
        
        return ResponseEntity.ok(auditLogs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> getAuditLog(@PathVariable Long id) {
        return auditLogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}