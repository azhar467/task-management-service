package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseService {

    @Autowired
    TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO){
        taskDTO.setStatus(taskDTO.getStatus().toUpperCase());
        taskDTO.setPriority(taskDTO.getPriority().toUpperCase());
        return new ResponseEntity<>(taskService.saveTask(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}",""})
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable(required = false) @NonNull Long id){
        if (id!=null){
            return new ResponseEntity<>(List.of(modelMapper.map(taskRepository.findById(id).orElseThrow(), TaskDTO.class)),HttpStatus.OK);
        }
        return new ResponseEntity<>(taskRepository.findById(id).stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        return ResponseEntity.ok(modelMapper.map(taskService.updateTask(id, taskDTO), TaskDTO.class));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

}
