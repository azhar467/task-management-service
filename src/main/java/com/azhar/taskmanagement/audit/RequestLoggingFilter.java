package com.azhar.taskmanagement.audit;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingFilter implements Filter {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (shouldLog(httpRequest)) {
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);
            
            long startTime = System.currentTimeMillis();
            chain.doFilter(wrappedRequest, wrappedResponse);
            long executionTime = System.currentTimeMillis() - startTime;
            
            saveAuditLog(wrappedRequest, wrappedResponse, executionTime);
            wrappedResponse.copyBodyToResponse();
        } else {
            chain.doFilter(request, response);
        }
    }
    
    private boolean shouldLog(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        return ("POST".equals(method) || "PUT".equals(method)) && uri.startsWith("/api/");
    }
    
    private void saveAuditLog(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long executionTime) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setRequestMethod(request.getMethod());
            auditLog.setRequestUri(request.getRequestURI());
            auditLog.setRequestPayload(new String(request.getContentAsByteArray(), StandardCharsets.UTF_8));
            auditLog.setResponsePayload(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
            auditLog.setResponseStatus(response.getStatus());
            auditLog.setUserAgent(request.getHeader("User-Agent"));
            auditLog.setIpAddress(getClientIpAddress(request));
            auditLog.setExecutionTimeMs(executionTime);
            auditLog.setCreatedAt(LocalDateTime.now());
            
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.error("Failed to save audit log: {}", e.getMessage());
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}