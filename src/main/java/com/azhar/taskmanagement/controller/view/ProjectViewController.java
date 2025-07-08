package com.azhar.taskmanagement.controller.view;

import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectViewController {
    private final ProjectService projectService;

    @Autowired
    public ProjectViewController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects/project-list";
    }

    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProjectById(id));
        return "projects/project-detail";
    }

    @GetMapping("/new")
    public String showProjectForm(Model model) {
        model.addAttribute("project", new ProjectDTO());
        return "projects/project-form";
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute ProjectDTO project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProjectById(id));
        return "projects/project-form"; // Reuse the form for editing
    }

    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute ProjectDTO projectDTO) {
        projectService.updateProjectDetails(id, projectDTO);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
