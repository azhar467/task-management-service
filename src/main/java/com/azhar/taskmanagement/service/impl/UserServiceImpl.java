package com.azhar.taskmanagement.service.impl;

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
        User user = modelMapper.map(userdto,User.class);
        if (userdto.getProjectIds()!=null){
            user.setProjects(projectRepository.findAllById(userdto.getProjectIds()));
        }
        if (userdto.getTaskIds()!=null){
            user.setTasks(taskRepository.findAllById(userdto.getTaskIds()));
        }
        User savedUser = userRepository.save(user);
        log.info("Id of the saved user: {}", savedUser.getId());
        return modelMapper.map(savedUser,UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Total Number of Users: {}", users.size());
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(),UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User dbUser = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" UserId: ",id));
        dbUser.setName(userDTO.getName());
        dbUser.setUpdatedAt(LocalDateTime.now());
        dbUser.setEmail(userDTO.getEmail());
        dbUser.setRole(UserRole.valueOf(userDTO.getRole()));
        dbUser.setGender(Gender.valueOf(userDTO.getGender()));
        dbUser.setDateOfBirth(userDTO.getDateOfBirth());
        if (userDTO.getTaskIds()!=null && userDTO.getTaskIds().stream().findFirst().orElseThrow()>0){
            List<Task> tasks = taskRepository.findAllById(userDTO.getTaskIds());
            for (Task task:tasks){
                task.setAssignee(dbUser);
            }
        }
        if (userDTO.getProjectIds()!=null && userDTO.getProjectIds().stream().findFirst().orElseThrow()>0){
            List<Project> projects = projectRepository.findAllById(userDTO.getProjectIds());
            for (Project project: projects){
                project.setCreatedById(dbUser);
            }
        }
        return modelMapper.map(userRepository.save(dbUser), UserDTO.class);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
