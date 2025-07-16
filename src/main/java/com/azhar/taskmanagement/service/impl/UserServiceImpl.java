package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.mappers.UserMapper;
import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.dao.enums.Gender;
import com.azhar.taskmanagement.dao.enums.UserRole;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.UserService;
import com.azhar.taskmanagement.service.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserValidator userValidator;
    
    public UserServiceImpl(UserRepository userRepository, ProjectRepository projectRepository,
                          TaskRepository taskRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userValidator = userValidator;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO){
        userValidator.validateUserCreation(userDTO);
        
        List<Project> projects = projectRepository.findAll();
        List<Task> tasks = taskRepository.findAll();
        User user = UserMapper.toEntity(userDTO, projects, tasks);
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
    public List<UserDTO> getFilteredUsers(String role) {
        if (role == null || role.isEmpty()) {
            return getAllUsers();
        }
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        return userRepository.findByRole(userRole).stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id)));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        userValidator.validateUserUpdate(userDTO);
        
        User dbUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User", id));
        
        dbUser.setName(userDTO.getName());
        dbUser.setUpdatedAt(LocalDateTime.now());
        dbUser.setEmail(userDTO.getEmail());
        dbUser.setRole(UserRole.valueOf(userDTO.getRole().toUpperCase()));
        dbUser.setGender(Gender.valueOf(userDTO.getGender().toUpperCase()));
        dbUser.setDateOfBirth(userDTO.getDateOfBirth());
        
        if (!userDTO.getTaskIds().isEmpty() && userDTO.getTaskIds().stream().findFirst().orElse(0L) > 0) {
            List<Task> tasks = taskRepository.findAllById(userDTO.getTaskIds());
            tasks.forEach(task -> task.setAssignee(dbUser));
        }
        
        if (!userDTO.getProjectIds().isEmpty() && userDTO.getProjectIds().stream().findFirst().orElse(0L) > 0) {
            List<Project> projects = projectRepository.findAllById(userDTO.getProjectIds());
            projects.forEach(project -> project.setCreatedBy(dbUser));
        }
        
        return UserMapper.toDto(userRepository.save(dbUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
