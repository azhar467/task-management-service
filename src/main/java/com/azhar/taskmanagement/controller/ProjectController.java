package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.dao.dto.ProjectDTO;
import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController extends BaseService {

    @Autowired
    protected ProjectService projectService;

    @PostMapping("/addProject")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO){
        return new ResponseEntity<>(projectService.saveProject(projectDTO), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}",""})
    public ResponseEntity<List<ProjectDTO>> getProjects(@PathVariable(required = false) Long id){
        if (id!=null){
            return new ResponseEntity<>(List.of(projectService.getProjectById(id)),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(projectService.getAllProjects(),HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") Long id,@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.updateProjectDetails(id, projectDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteMapping(@PathVariable("id") Long id){
        projectService.deleteProject(id);
    }
}
