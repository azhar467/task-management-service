package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseService {

    @Autowired
    protected UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<CompletableFuture<UserDTO>> addUser(@ParameterObject @RequestBody UserDTO userDTO){
        userDTO.setRole(userDTO.getRole().toUpperCase());
        userDTO.setGender(userDTO.getGender().toUpperCase());
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}",""})
    public ResponseEntity<CompletableFuture<List<UserDTO>>> getUsers(@PathVariable(required = false) Long id){
        if (id!=null){
            return ResponseEntity.ok(userService.getUserById(id).thenApply(List::of));
        } else {
            return ResponseEntity.ok(userService.getAllUsers());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompletableFuture<UserDTO>> updateUser(@PathVariable Long id, @ParameterObject @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id,userDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<CompletableFuture<Void>> deleteUser(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
