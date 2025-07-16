package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.service.interfaces.EntityService;

public interface TaskService extends EntityService<TaskDTO, Long> {
    TaskDTO saveTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    
    @Override
    default TaskDTO save(TaskDTO entity) {
        return saveTask(entity);
    }
    
    @Override
    default TaskDTO update(Long id, TaskDTO entity) {
        return updateTask(id, entity);
    }
    
    @Override
    default TaskDTO findById(Long id) {
        return getTaskById(id);
    }
    
    @Override
    default java.util.List<TaskDTO> findAll() {
        return getAllTasks();
    }
    
    @Override
    default void deleteById(Long id) {
        deleteTask(id);
    }
    
    java.util.List<TaskDTO> getAllTasks();
    java.util.List<TaskDTO> getFilteredTasks(Long projectId, Long assigneeId, String status);
    TaskDTO getTaskById(Long id);
    void deleteTask(Long id);
}
