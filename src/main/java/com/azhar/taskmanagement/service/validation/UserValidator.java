package com.azhar.taskmanagement.service.validation;

import com.azhar.taskmanagement.dao.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    
    public void validateUserCreation(UserDTO userDTO) {
        validateUserData(userDTO);
    }
    
    public void validateUserUpdate(UserDTO userDTO) {
        validateUserData(userDTO);
    }
    
    private void validateUserData(UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be empty");
        }
    }
}