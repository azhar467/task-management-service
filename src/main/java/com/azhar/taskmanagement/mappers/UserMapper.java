package com.azhar.taskmanagement.mappers;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.dao.enums.Gender;
import com.azhar.taskmanagement.dao.enums.UserRole;

import java.util.List;

public class UserMapper {

    private UserMapper(){
        throw new IllegalStateException("Utility class");
    }
    // Convert User entity to UserDTO
    public static UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().toString());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setGender(user.getGender().toString());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        // Set only project IDs in DTO
        if (user.getProjects() != null) {
            List<Long> projectIds = user.getProjects()
                    .stream()
                    .map(Project::getId) // Extract only the project IDs
                    .toList();
            userDTO.setProjectIds(projectIds);
        }

        // Convert and set tasks to DTOs
        if (user.getTasks() != null) {
            List<Long> taskIds = user.getTasks()
                    .stream()
                    .map(Task::getId) // Assuming TaskMapper exists
                    .toList();
            userDTO.setTaskIds(taskIds);
        }

        return userDTO;
    }

    // Convert UserDTO to User entity
    public static User toEntity(UserDTO userDTO, List<Project> projects, List<Task> availableTasks) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.valueOf(userDTO.getRole())); // Convert string role to UserRole enum
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setGender(Gender.valueOf(userDTO.getGender())); // Convert string gender to Gender enum

        // Set projects to the User entity by matching IDs
        if (userDTO.getProjectIds() != null && projects != null) {
            List<Project> matchingProjects = projects.stream()
                    .filter(project -> userDTO.getProjectIds().contains(project.getId()))
                    .toList();
            user.setProjects(matchingProjects);
        }

        // Convert and set tasks to entities based on task IDs
        if (userDTO.getTaskIds() != null && availableTasks != null) {
            List<Task> tasks = availableTasks.stream()
                    .filter(task -> userDTO.getTaskIds().contains(task.getId())) // Use getId() for Task ID
                    .toList();
            user.setTasks(tasks);
        }

        return user;
    }

}
