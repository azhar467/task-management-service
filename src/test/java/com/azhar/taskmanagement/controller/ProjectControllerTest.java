package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProject_ShouldReturnCreatedProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");
        projectDTO.setDescription("Test Description");
        projectDTO.setCreatedById(1L);
        projectDTO.setStartDate(LocalDate.now());
        projectDTO.setEndDate(LocalDate.now().plusDays(30));

        ProjectDTO savedProject = new ProjectDTO();
        savedProject.setId(1L);
        savedProject.setName("Test Project");
        savedProject.setDescription("Test Description");

        when(projectService.saveProject(any(ProjectDTO.class))).thenReturn(savedProject);

        mockMvc.perform(post("/api/projects/addProject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    void getProjects_WithId_ShouldReturnSingleProject() throws Exception {
        ProjectDTO project = new ProjectDTO();
        project.setId(1L);
        project.setName("Test Project");

        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Project"));
    }

    @Test
    void getProjects_WithoutId_ShouldReturnAllProjects() throws Exception {
        List<ProjectDTO> projects = Arrays.asList(
                new ProjectDTO(1L, "Project 1", "Description 1", 1L, null, null, null, null, null),
                new ProjectDTO(2L, "Project 2", "Description 2", 2L, null, null, null, null, null)
        );

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateProject_ShouldReturnUpdatedProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        ProjectDTO updatedProject = new ProjectDTO();
        updatedProject.setId(1L);
        updatedProject.setName("Updated Project");
        updatedProject.setDescription("Updated Description");

        when(projectService.updateProjectDetails(eq(1L), any(ProjectDTO.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Project"));
    }

    @Test
    void deleteProject_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk());
    }
}