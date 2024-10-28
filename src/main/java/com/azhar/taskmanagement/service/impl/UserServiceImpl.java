package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.mappers.UserMapper;
import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;

import com.azhar.taskmanagement.dao.enums.Gender;
import com.azhar.taskmanagement.dao.enums.UserRole;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class UserServiceImpl extends BaseService implements UserService {

    @Override
    public UserDTO saveUser(UserDTO userdto){
        List<Project> projects = projectRepository.findAll();
        List<Task> tasks = taskRepository.findAll();
        User user = UserMapper.toEntity(userdto,projects,tasks);
        User savedUser = userRepository.save(user);
        log.info("Id of the saved user: {}", savedUser.getId());
        return UserMapper.toDto(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Total Number of Users: {}", users.size());
        return users.stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: ",id)));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User dbUser = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" UserId: ",id));
        dbUser.setName(userDTO.getName());
        dbUser.setUpdatedAt(LocalDateTime.now());
        dbUser.setEmail(userDTO.getEmail());
        dbUser.setRole(UserRole.valueOf(userDTO.getRole().toUpperCase()));
        dbUser.setGender(Gender.valueOf(userDTO.getGender().toUpperCase()));
        dbUser.setDateOfBirth(userDTO.getDateOfBirth());
        if (!userDTO.getTaskIds().isEmpty() && userDTO.getTaskIds().stream().findFirst().orElseThrow()>0){
            List<Task> tasks = taskRepository.findAllById(userDTO.getTaskIds());
            for (Task task:tasks){
                task.setAssignee(dbUser);
            }
        }
        if (!userDTO.getProjectIds().isEmpty() && userDTO.getProjectIds().stream().findFirst().orElseThrow()>0){
            List<Project> projects = projectRepository.findAllById(userDTO.getProjectIds());
            for (Project project: projects){
                project.setCreatedBy(dbUser);
            }
        }
        return UserMapper.toDto(userRepository.save(dbUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
