package com.azhar.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private String name;
    private String description;
    private Long createdById;  // Admin/User who created the project
    private String createdByName;  // Name of the user who created the project
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> taskIds;  // List of Task IDs associated with the project

}
