package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.mappers.ProjectMapper;
import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.ProjectService;
import com.azhar.taskmanagement.service.validation.ProjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectValidator projectValidator;
    
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository,
                             TaskRepository taskRepository, ProjectValidator projectValidator) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectValidator = projectValidator;
    }

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        projectValidator.validateProjectCreation(projectDTO);
        
        User user = userRepository.findById(projectDTO.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("User", projectDTO.getCreatedById()));

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setCreatedBy(user);
        
        user.addProject(project);
        Project savedProject = projectRepository.save(project);
        return ProjectMapper.toDto(savedProject);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects= projectRepository.findAll();
        return projects.stream().map(ProjectMapper::toDto).toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return ProjectMapper.toDto(projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Project", id)));
    }

    @Override
    public ProjectDTO updateProjectDetails(Long id, ProjectDTO projectDTO) {
        projectValidator.validateProjectUpdate(projectDTO);
        
        Project dbProject = projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Project", id));
        
        dbProject.setName(projectDTO.getName());
        dbProject.setDescription(projectDTO.getDescription());
        dbProject.setStartDate(projectDTO.getStartDate());
        dbProject.setEndDate(projectDTO.getEndDate());
        dbProject.setUpdatedAt(LocalDateTime.now());
        
        if (projectDTO.getTaskIds() != null) {
            List<Task> tasks = taskRepository.findAllById(projectDTO.getTaskIds());
            dbProject.setTasks(tasks);
        }
        
        if (projectDTO.getCreatedById() != null) {
            User user = userRepository.findById(projectDTO.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("User", projectDTO.getCreatedById()));
            dbProject.setCreatedBy(user);
        }
        
        return ProjectMapper.toDto(projectRepository.save(dbProject));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
