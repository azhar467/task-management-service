package com.azhar.taskmanagement.dao.dto;

import com.azhar.taskmanagement.dao.enums.Gender;
import com.azhar.taskmanagement.dao.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
    @Schema(anyOf = Gender.class)
    private String gender;
    @Schema(description = "User's date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
    private String email;
    @Schema(anyOf = UserRole.class)
    private String role;  // Use UserRole Enum in String format
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private List<Long> taskIds;  // List of Task IDs assigned to the user
    private List<Long> projectIds;  // List of Project IDs created by the user

}
