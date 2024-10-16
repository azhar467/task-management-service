package com.azhar.taskmanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Test Project");
        projectDTO.setCreatedById(1L);

        User mockUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Project mockProject = new Project();
        when(projectRepository.save(any(Project.class))).thenReturn(mockProject);

        ProjectDTO savedProjectDTO = projectService.saveProject(projectDTO);

        verify(userRepository).findById(1L);
        verify(projectRepository).save(any(Project.class));

        assertNotNull(savedProjectDTO);
    }

    @Test
    void testSaveProject_UserNotFound() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setCreatedById(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            projectService.saveProject(projectDTO);
        });

        verify(userRepository).findById(1L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void testGetAllProjects() {
        List<Project> mockProjects = List.of(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(mockProjects);

        List<ProjectDTO> projectDTOList = projectService.getAllProjects();

        verify(projectRepository).findAll();
        assertEquals(2, projectDTOList.size());
    }

    @Test
    void testGetProjectById() {
        Project mockProject = new Project();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));

        ProjectDTO projectDTO = projectService.getProjectById(1L);

        verify(projectRepository).findById(1L);
        assertNotNull(projectDTO);
    }

    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            projectService.getProjectById(1L);
        });

        verify(projectRepository).findById(1L);
    }

    @Test
    void testUpdateProjectDetails() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");

        Project mockProject = new Project();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(projectRepository.save(mockProject)).thenReturn(mockProject);

        ProjectDTO updatedProjectDTO = projectService.updateProjectDetails(1L, projectDTO);

        verify(projectRepository).findById(1L);
        verify(projectRepository).save(mockProject);

        assertEquals("Updated Project", mockProject.getName());
        assertNotNull(updatedProjectDTO);
    }

    @Test
    void testDeleteProject() {
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository).deleteById(1L);
    }
}
