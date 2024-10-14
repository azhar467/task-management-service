package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.dao.enums.TaskStatus;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class TaskServiceImpl extends BaseService implements TaskService {
    @Override
    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setProject(projectRepository.findById(taskDTO.getProjectId()).orElseThrow(() -> new EntityNotFoundException("Project: ", taskDTO.getProjectId())));
        task.setAssignee(userRepository.findById(taskDTO.getAssigneeId()).orElseThrow(() -> new EntityNotFoundException("User: ",taskDTO.getAssigneeId())));
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskDTO.class);
    }

    @Override
    public List<TaskDTO> getAllUsers() {
        return taskRepository.findAll().stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList();
    }

    @Override
    public TaskDTO getUserById(Long id) {
        return modelMapper.map(taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task with Id: ",id)), TaskDTO.class);
    }

    @Override
    public TaskDTO updateUser(Long id, TaskDTO taskDTO) {
        Task dbTask = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task with id: ",id));
        dbTask.setTitle(taskDTO.getTitle());
        dbTask.setDescription(taskDTO.getDescription());
        dbTask.setDueDate(taskDTO.getDueDate());
        dbTask.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        dbTask.setUpdatedAt(LocalDateTime.now());
        if (taskDTO.getAssigneeName()!=null){
            User assigneeName = userRepository.findByName(taskDTO.getAssigneeName());
            dbTask.setAssignee(assigneeName);
        }
        if (taskDTO.getProjectName()!=null){
            dbTask.setProject(projectRepository.findByName(taskDTO.getProjectName()));
        }
        dbTask.setDueDate(taskDTO.getDueDate());
        return modelMapper.map(dbTask,TaskDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        taskRepository.deleteById(id);
    }
}
