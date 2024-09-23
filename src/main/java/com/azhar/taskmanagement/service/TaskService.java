package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskDTO saveTask(TaskDTO taskDTO);
    List<TaskDTO> getAllUsers();
    TaskDTO getUserById(Long id);
    TaskDTO updateUser(Long id,TaskDTO taskDTO);
    void deleteUser(Long id);
}
