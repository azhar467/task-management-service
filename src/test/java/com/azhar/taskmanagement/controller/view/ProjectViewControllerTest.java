package com.azhar.taskmanagement.controller.view;

import com.azhar.taskmanagement.audit.AuditLogRepository;
import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectViewController.class)
@ActiveProfiles("local")
class ProjectViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private AuditLogRepository auditLogRepository;

    @Test
    void listProjects_ShouldReturnProjectListView() throws Exception {
        List<ProjectDTO> projects = Arrays.asList(
                new ProjectDTO(1L, "Project 1", "Description 1", 1L, null, null, LocalDate.now(), LocalDate.now().plusDays(30), null),
                new ProjectDTO(2L, "Project 2", "Description 2", 2L, null, null, LocalDate.now(), LocalDate.now().plusDays(60), null)
        );

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/project-list"))
                .andExpect(model().attributeExists("projects"));
    }

    @Test
    void viewProject_ShouldReturnProjectDetailView() throws Exception {
        ProjectDTO project = new ProjectDTO();
        project.setId(1L);
        project.setName("Test Project");

        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/project-detail"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void showProjectForm_ShouldReturnProjectFormView() throws Exception {
        mockMvc.perform(get("/projects/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/project-form"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void saveProject_ShouldRedirectToProjectsList() throws Exception {
        ProjectDTO savedProject = new ProjectDTO();
        savedProject.setId(1L);

        when(projectService.saveProject(any(ProjectDTO.class))).thenReturn(savedProject);

        mockMvc.perform(post("/projects/save")
                .param("name", "Test Project")
                .param("description", "Test Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    void editProject_ShouldReturnProjectFormView() throws Exception {
        ProjectDTO project = new ProjectDTO();
        project.setId(1L);
        project.setName("Test Project");

        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/projects/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/project-form"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void updateProject_ShouldRedirectToProjectsList() throws Exception {
        ProjectDTO updatedProject = new ProjectDTO();
        updatedProject.setId(1L);

        when(projectService.updateProjectDetails(eq(1L), any(ProjectDTO.class))).thenReturn(updatedProject);

        mockMvc.perform(post("/projects/update/1")
                .param("name", "Updated Project")
                .param("description", "Updated Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    void deleteProject_ShouldRedirectToProjectsList() throws Exception {
        mockMvc.perform(get("/projects/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }
}