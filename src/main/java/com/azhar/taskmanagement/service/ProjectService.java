package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.service.interfaces.EntityService;

public interface ProjectService extends EntityService<ProjectDTO, Long> {
    ProjectDTO saveProject(ProjectDTO projectDTO);
    ProjectDTO updateProjectDetails(Long id, ProjectDTO projectDTO);
    
    @Override
    default ProjectDTO save(ProjectDTO entity) {
        return saveProject(entity);
    }
    
    @Override
    default ProjectDTO update(Long id, ProjectDTO entity) {
        return updateProjectDetails(id, entity);
    }
    
    @Override
    default ProjectDTO findById(Long id) {
        return getProjectById(id);
    }
    
    @Override
    default java.util.List<ProjectDTO> findAll() {
        return getAllProjects();
    }
    
    @Override
    default void deleteById(Long id) {
        deleteProject(id);
    }
    
    java.util.List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Long id);
    void deleteProject(Long id);
}
