package com.azhar.taskmanagement.service.validation;

import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {
    
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    
    public TaskValidator(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
    
    public void validateTaskCreation(TaskDTO taskDTO) {
        validateAssigneeExists(taskDTO.getAssigneeId());
        validateProjectExists(taskDTO.getProjectId());
        validateTaskData(taskDTO);
    }
    
    public void validateTaskUpdate(TaskDTO taskDTO) {
        validateTaskData(taskDTO);
        if (taskDTO.getAssigneeId() != null) {
            validateAssigneeExists(taskDTO.getAssigneeId());
        }
        if (taskDTO.getProjectId() != null) {
            validateProjectExists(taskDTO.getProjectId());
        }
    }
    
    private void validateAssigneeExists(Long assigneeId) {
        if (!userRepository.existsById(assigneeId)) {
            throw new EntityNotFoundException("User", assigneeId);
        }
    }
    
    private void validateProjectExists(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project", projectId);
        }
    }
    
    private void validateTaskData(TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
    }
}