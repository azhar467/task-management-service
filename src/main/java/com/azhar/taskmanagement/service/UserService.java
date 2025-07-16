package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.service.interfaces.EntityService;

public interface UserService extends EntityService<UserDTO, Long> {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    
    @Override
    default UserDTO save(UserDTO entity) {
        return saveUser(entity);
    }
    
    @Override
    default UserDTO update(Long id, UserDTO entity) {
        return updateUser(id, entity);
    }
    
    @Override
    default UserDTO findById(Long id) {
        return getUserById(id);
    }
    
    @Override
    default java.util.List<UserDTO> findAll() {
        return getAllUsers();
    }
    
    @Override
    default void deleteById(Long id) {
        deleteUser(id);
    }
    
    java.util.List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
}
