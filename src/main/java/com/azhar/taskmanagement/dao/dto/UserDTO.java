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
public class UserDTO {

    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private String role;  // Use UserRole Enum in String format
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> taskIds;  // List of Task IDs assigned to the user
    private List<Long> projectIds;  // List of Project IDs created by the user

}
