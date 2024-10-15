package com.azhar.taskmanagement.service;


import com.azhar.taskmanagement.dao.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDTO saveUser(UserDTO userdto);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id,UserDTO userDTO);
    void deleteUser(Long id);
}
