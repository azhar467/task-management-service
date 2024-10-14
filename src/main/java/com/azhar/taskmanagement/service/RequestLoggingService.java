package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.RequestLog;
import com.azhar.taskmanagement.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLoggingService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    public void saveRequest(String method, String uri, String headers, String queryParams, String requestBody) {
        if (isIgnoredRequest(uri)) return;
        RequestLog log = new RequestLog();
        log.setMethod(method);
        log.setEndpoint(uri);
        log.setHeaders(headers); // Convert headers to JSON
        log.setQueryParams(queryParams); // Convert query params to JSON
        log.setRequestBody(requestBody); // Convert body to JSON
        requestLogRepository.save(log);
    }

    private boolean isIgnoredRequest(String requestURI) {
        return requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars/") ||
                requestURI.startsWith("/v2/api-docs") ||
                requestURI.equals("/favicon.ico");  // Ignoring favicon requests
    }
}
