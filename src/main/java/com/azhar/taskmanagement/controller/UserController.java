package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseService {

    @Autowired
    protected UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        userDTO.setRole(userDTO.getRole().toUpperCase());
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}",""})
    public ResponseEntity<List<UserDTO>> getUsers(@PathVariable(required = false) Long id){
        if (id!=null){
            return new ResponseEntity<>(List.of(userService.getUserById(id)),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id,userDTO));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id){
        userService.deleteUser(id);
    }
}
