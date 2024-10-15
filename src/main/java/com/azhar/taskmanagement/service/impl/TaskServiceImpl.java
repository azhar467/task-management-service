package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.mappers.TaskMapper;
import com.azhar.taskmanagement.dao.Project;
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
        User user = userRepository.findById(taskDTO.getAssigneeId()).get();
        Project project = projectRepository.findById(taskDTO.getProjectId()).get();
        Task task = TaskMapper.toEntity(taskDTO, user,project);
        task.setProject(project);
        User assignee = userRepository.findById(taskDTO.getAssigneeId()).orElseThrow(() -> new EntityNotFoundException("User: ",taskDTO.getAssigneeId()));
        task.setAssignee(assignee);
        Task savedTask = taskRepository.save(task);
        userRepository.save(assignee);
        return TaskMapper.toDto(savedTask);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return TaskMapper.toDto(taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task with Id: ",id)));
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
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
        return TaskMapper.toDto(dbTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
