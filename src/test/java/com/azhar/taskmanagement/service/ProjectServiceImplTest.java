package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.impl.ProjectServiceImpl;
import com.azhar.taskmanagement.service.validation.ProjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
    private ProjectValidator projectValidator;

    private Project project;
    private ProjectDTO projectDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setCreatedBy(user);
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(30));

        projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");
        projectDTO.setDescription("Test Description");
        projectDTO.setCreatedById(1L);
        projectDTO.setStartDate(LocalDate.now());
        projectDTO.setEndDate(LocalDate.now().plusDays(30));
    }

    @Test
    void saveProject_ShouldReturnSavedProjectDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO result = projectService.saveProject(projectDTO);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void saveProject_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectService.saveProject(projectDTO));
    }

    @Test
    void getAllProjects_ShouldReturnListOfProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));

        List<ProjectDTO> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).getName());
    }

    @Test
    void getProjectById_ShouldReturnProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectDTO result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
    }

    @Test
    void getProjectById_ShouldThrowException_WhenProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectService.getProjectById(1L));
    }

    @Test
    void updateProjectDetails_ShouldReturnUpdatedProject() {
        projectDTO.setName("Updated Project");
        projectDTO.setTaskIds(Arrays.asList(1L));
        projectDTO.setCreatedById(1L);

        Task task = new Task();
        task.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findAllById(Arrays.asList(1L))).thenReturn(Arrays.asList(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO result = projectService.updateProjectDetails(1L, projectDTO);

        assertNotNull(result);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void deleteProject_ShouldCallRepository() {
        projectService.deleteProject(1L);

        verify(projectRepository).deleteById(1L);
    }
}