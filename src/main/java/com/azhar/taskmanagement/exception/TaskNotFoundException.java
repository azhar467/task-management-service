package com.azhar.taskmanagement.exception;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(Long taskId) {
        super("Task", taskId);
    }
}