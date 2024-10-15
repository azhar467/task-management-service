package com.azhar.taskmanagement.service;


import com.azhar.taskmanagement.dao.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface UserService {
    CompletableFuture<UserDTO> saveUser(UserDTO userdto);
    CompletableFuture<List<UserDTO>> getAllUsers();
    CompletableFuture<UserDTO> getUserById(Long id);
    CompletableFuture<UserDTO> updateUser(Long id,UserDTO userDTO);
    CompletableFuture<Void> deleteUser(Long id);
}
