package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskDTO saveTask(TaskDTO taskDTO);
    List<TaskDTO> getAllTasks();
    TaskDTO getTaskById(Long id);
    TaskDTO updateTask(Long id,TaskDTO taskDTO);
    void deleteTask(Long id);
}
