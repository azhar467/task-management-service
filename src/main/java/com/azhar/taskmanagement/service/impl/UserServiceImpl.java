package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;

import com.azhar.taskmanagement.dao.enums.UserRole;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;

    @Autowired private TaskRepository taskRepository;

    @Autowired private ProjectRepository projectRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO saveUser(UserDTO userdto){
        User user = modelMapper.map(userdto,User.class);
        User savedUser = userRepository.save(user);
        log.info("Id of the saved user: {}", savedUser.getId());
        return modelMapper.map(savedUser,UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Total Number of Users: {}", users.size());
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(),UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User dbUser = userRepository.findById(id).orElseThrow();
        dbUser.setName(userDTO.getName());
        dbUser.setUpdatedAt(LocalDateTime.now());
        dbUser.setEmail(userDTO.getEmail());
        dbUser.setRole(UserRole.valueOf(userDTO.getRole()));
        if (userDTO.getTaskIds()!=null && userDTO.getTaskIds().stream().findFirst().orElseThrow()>0){
            List<Task> tasks = taskRepository.findAllById(userDTO.getTaskIds());
            for (Task task:tasks){
                task.setAssignee(dbUser);
            }
        }
        if (userDTO.getProjectIds()!=null && userDTO.getProjectIds().stream().findFirst().orElseThrow()>0){
            List<Project> projects = projectRepository.findAllById(userDTO.getProjectIds());
            for (Project project: projects){
                project.setCreatedBy(dbUser);
            }
        }
        return modelMapper.map(userRepository.save(dbUser), UserDTO.class);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
