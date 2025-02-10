package com.azhar.taskmanagement.exception;

public class ProjectNotFoundException extends EntityNotFoundException {
    public ProjectNotFoundException(Long projectId) {
        super("Project", projectId);
    }
}