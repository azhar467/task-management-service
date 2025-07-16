package com.azhar.taskmanagement.service.validation;

import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidator {
    
    public void validateProjectCreation(ProjectDTO projectDTO) {
        validateProjectData(projectDTO);
    }
    
    public void validateProjectUpdate(ProjectDTO projectDTO) {
        validateProjectData(projectDTO);
    }
    
    private void validateProjectData(ProjectDTO projectDTO) {
        if (projectDTO.getName() == null || projectDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
        }
    }
}