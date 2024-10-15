package com.azhar.taskmanagement.mappers;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.dao.enums.TaskPriority;
import com.azhar.taskmanagement.dao.enums.TaskStatus;

public class TaskMapper {

    // Convert Task entity to TaskDTO
    public static TaskDTO toDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus().name()); // Convert Enum to String
        taskDTO.setPriority(task.getPriority().name()); // Convert Enum to String
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());

        // Set assignee details
        if (task.getAssignee() != null) {
            taskDTO.setAssigneeId(task.getAssignee().getId());
            taskDTO.setAssigneeName(task.getAssignee().getName());
        }

        // Set project details
        if (task.getProject() != null) {
            taskDTO.setProjectId(task.getProject().getId());
            taskDTO.setProjectName(task.getProject().getName());
        }

        return taskDTO;
    }

    // Convert TaskDTO to Task entity
    public static Task toEntity(TaskDTO taskDTO, User assignee, Project project) {
        if (taskDTO == null) {
            return null;
        }

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());

        // Convert String to Enum for TaskStatus and TaskPriority
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        task.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
        task.setDueDate(taskDTO.getDueDate());

        // Set assignee (if available)
        if (assignee != null) {
            task.setAssignee(assignee);
        }

        // Set project (if available)
        if (project != null) {
            task.setProject(project);
        }

        return task;
    }
}
