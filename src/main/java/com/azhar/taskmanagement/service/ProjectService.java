package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Long id);
    ProjectDTO updateProjectDetails(Long id,ProjectDTO projectDTO);
    void deleteProject(Long id);
}
