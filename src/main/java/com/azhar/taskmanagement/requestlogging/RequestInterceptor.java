package com.azhar.taskmanagement.requestlogging;

import com.azhar.taskmanagement.service.RequestLoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.util.Enumeration;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestLoggingService requestLoggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Capture HTTP method and endpoint URI
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // Capture headers
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append(", ");
        }

        // Capture query params
        String queryParams = request.getQueryString() != null ? request.getQueryString() : "{}";

        // Capture request body
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        // Save request log
        requestLoggingService.saveRequest(method, uri, headers.toString(), queryParams, requestBody.toString());

        return true; // Proceed with the request
    }
}
