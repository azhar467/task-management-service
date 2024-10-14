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
        RequestLog log = new RequestLog();
        log.setMethod(method);
        log.setEndpoint(uri);
        log.setHeaders(headers); // Convert headers to JSON
        log.setQueryParams(queryParams); // Convert query params to JSON
        log.setRequestBody(requestBody); // Convert body to JSON
        requestLogRepository.save(log);
    }
}
