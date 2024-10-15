package com.azhar.taskmanagement.mappers;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.ProjectDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {

    // Convert Project entity to ProjectDTO
    public static ProjectDTO toDto(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setEndDate(project.getEndDate());
        projectDTO.setCreatedAt(project.getCreatedAt());
        projectDTO.setUpdatedAt(project.getUpdatedAt());

        // Convert and set User (createdBy) to DTO
        if (project.getCreatedBy() != null) {
            projectDTO.setCreatedById(project.getCreatedBy().getId()); // Using the ID of the createdBy user
        }

        // Convert and set tasks to TaskDTOs
        if (project.getTasks() != null) {
            List<Long> taskIds = project.getTasks()
                    .stream()
                    .map(Task::getId) // Extract the task ID
                    .collect(Collectors.toList());
            projectDTO.setTaskIds(taskIds); // Set the list of task IDs to the DTO
        }

        return projectDTO;
    }

    // Convert ProjectDTO to Project entity
    public static Project toEntity(ProjectDTO projectDTO, List<User> availableUsers, List<Task> availableTasks) {
        if (projectDTO == null) {
            return null;
        }

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());

        // Convert and set User (createdBy) to entity based on available user IDs
        if (projectDTO.getCreatedById() != null && availableUsers != null) {
            User createdBy = availableUsers.stream()
                    .filter(user -> user.getId().equals(projectDTO.getCreatedById()))
                    .findFirst()
                    .orElse(null);
            project.setCreatedBy(createdBy); // Set the matching User entity
        }

        // Convert and set tasks to entities based on task IDs
        if (projectDTO.getTaskIds() != null && availableTasks != null) {
            List<Task> tasks = availableTasks.stream()
                    .filter(task -> projectDTO.getTaskIds().contains(task.getId()))
                    .collect(Collectors.toList());
            project.setTasks(tasks);  // Set the matching Task entities
        }

        return project;
    }
}
