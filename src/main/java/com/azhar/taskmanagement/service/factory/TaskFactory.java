package com.azhar.taskmanagement.service.factory;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.dao.enums.TaskPriority;
import com.azhar.taskmanagement.dao.enums.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskFactory {
    
    public Task createTask(TaskDTO taskDTO, User assignee, Project project) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        task.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
        task.setDueDate(taskDTO.getDueDate());
        task.setAssignee(assignee);
        task.setProject(project);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return task;
    }
    
    public void updateTask(Task existingTask, TaskDTO taskDTO) {
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        existingTask.setPriority(TaskPriority.valueOf(taskDTO.getPriority()));
        existingTask.setDueDate(taskDTO.getDueDate());
        existingTask.setUpdatedAt(LocalDateTime.now());
    }
}