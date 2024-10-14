package com.azhar.taskmanagement.service.impl;

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
        Project project = modelMapper.map(projectDTO, Project.class);
        project.setCreatedById(userRepository.findById(projectDTO.getCreatedById()).orElseThrow());
        if (projectDTO.getTaskIds()!=null && projectDTO.getTaskIds().stream().findFirst().get()>0){
            project.setTasks(taskRepository.findAllById(projectDTO.getTaskIds()));
        }
        Project savedProject = projectRepository.save(project);
        log.info("Saved project with Id {}",savedProject.getId());
        return modelMapper.map(savedProject,ProjectDTO.class);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects= projectRepository.findAll();
        return projects.stream().map(project -> modelMapper.map(project, ProjectDTO.class)).toList();
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return modelMapper.map(projectRepository.findById(id).orElseThrow(), ProjectDTO.class);
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
            dbProject.setCreatedById(user);
        }
        return modelMapper.map(dbProject, ProjectDTO.class);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
