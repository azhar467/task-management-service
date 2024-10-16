package com.azhar.taskmanagement.service.impl;

import com.azhar.taskmanagement.mappers.ProjectMapper;
import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ProjectServiceImpl extends BaseService implements ProjectService {

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        // Fetch the user who will be associated with the project
        User user = userRepository.findById(projectDTO.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: ", projectDTO.getCreatedById()));

        // Create a new Project entity from the DTO
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());

        // Set the User (creator) in the Project entity
        project.setCreatedBy(user);

        // Add the project to the user (to maintain bidirectional relationship)
        user.addProject(project);

        // Save the project
        Project savedProject = projectRepository.save(project);

        // Return the saved project as a DTO
        return ProjectMapper.toDto(savedProject);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects= projectRepository.findAll();
        return projects.stream().map(ProjectMapper::toDto).toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return ProjectMapper.toDto(projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project with {} not found",id)));
    }

    @Override
    public ProjectDTO updateProjectDetails(Long id, ProjectDTO projectDTO) {
        Project dbProject = projectRepository.findById(id).orElseThrow();
        dbProject.setName(projectDTO.getName());
        dbProject.setDescription(projectDTO.getDescription());
        dbProject.setStartDate(projectDTO.getStartDate());
        dbProject.setEndDate(projectDTO.getEndDate());
        dbProject.setUpdatedAt(LocalDateTime.now());
        if (projectDTO.getTaskIds()!=null){
            List<Task> tasks = taskRepository.findAllById(projectDTO.getTaskIds());
            dbProject.setTasks(tasks);
        }
        if (projectDTO.getCreatedById()!=null){
            User user = userRepository.findById(projectDTO.getCreatedById()).orElseThrow(() -> new EntityNotFoundException("UserId: ",projectDTO.getCreatedById()));
            dbProject.setCreatedBy(user);
        }
        return ProjectMapper.toDto(projectRepository.save(dbProject));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
