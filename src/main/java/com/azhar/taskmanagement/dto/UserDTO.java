package com.azhar.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String role;  // Use UserRole Enum in String format
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> taskIds;  // List of Task IDs assigned to the user
    private List<Long> projectIds;  // List of Project IDs created by the user

}
