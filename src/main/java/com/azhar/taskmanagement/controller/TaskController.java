package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.mappers.TaskMapper;
import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseService {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }


    @PostMapping("/addTask")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO){
        taskDTO.setStatus(taskDTO.getStatus().toUpperCase());
        taskDTO.setPriority(taskDTO.getPriority().toUpperCase());
        return new ResponseEntity<>(taskService.saveTask(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}",""})
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable(required = false) Long id){
        if (id!=null){
            return new ResponseEntity<>(List.of(TaskMapper.toDto(taskRepository.findById(id).orElseThrow())),HttpStatus.OK);
        }
        return new ResponseEntity<>(taskRepository.findById(id).stream().map(TaskMapper::toDto).toList(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

}
