package com.azhar.taskmanagement.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleNotFoundException_ShouldReturnNotFoundResponse() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found", 1L);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Entity not found with Id 1 Not Found", response.getBody().getMessage());
        assertEquals("The requested entity was not found in the system", response.getBody().getDetails());
        assertNotNull(response.getBody().getTimestamp());
    }
}