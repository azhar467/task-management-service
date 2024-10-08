package com.azhar.taskmanagement.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private String description;
    private String status;  // Use TaskStatus Enum in String format
    private String priority;  // Use TaskPriority Enum in String format
    private Long assigneeId;
    private String assigneeName;  // To show the assignee's name in the response
    private Long projectId;
    private String projectName;  // To show the project name in the response
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
