package com.azhar.taskmanagement.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String name;
    private String description;
    private Long createdById;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> taskIds;  // List of Task IDs associated with the project
}
