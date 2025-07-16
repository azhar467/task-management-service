package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.mappers.TaskMapper;
import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.dao.enums.TaskStatus;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.TaskService;
import com.azhar.taskmanagement.service.validation.TaskValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskValidator taskValidator;
    
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, 
                          ProjectRepository projectRepository, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskValidator = taskValidator;
    }
    @Override
    public TaskDTO saveTask(TaskDTO taskDTO) {
        taskValidator.validateTaskCreation(taskDTO);
        
        User assignee = userRepository.findById(taskDTO.getAssigneeId())
            .orElseThrow(() -> new EntityNotFoundException("User", taskDTO.getAssigneeId()));
        Project project = projectRepository.findById(taskDTO.getProjectId())
            .orElseThrow(() -> new EntityNotFoundException("Project", taskDTO.getProjectId()));
        
        Task task = TaskMapper.toEntity(taskDTO, assignee, project);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDto(savedTask);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(TaskMapper::toDto).toList();
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return TaskMapper.toDto(taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Task", id)));
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        taskValidator.validateTaskUpdate(taskDTO);
        
        Task dbTask = taskRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Task", id));
        
        dbTask.setTitle(taskDTO.getTitle());
        dbTask.setDescription(taskDTO.getDescription());
        dbTask.setDueDate(taskDTO.getDueDate());
        dbTask.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        dbTask.setUpdatedAt(LocalDateTime.now());
        
        if (taskDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskDTO.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("User", taskDTO.getAssigneeId()));
            dbTask.setAssignee(assignee);
        }
        
        if (taskDTO.getProjectId() != null) {
            Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project", taskDTO.getProjectId()));
            dbTask.setProject(project);
        }
        
        return TaskMapper.toDto(taskRepository.save(dbTask));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
