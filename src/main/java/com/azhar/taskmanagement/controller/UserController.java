package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController extends BaseService {

    protected final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    @Operation(summary = "Create a new user", description = "Creates a new user in the system.",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,content = @Content(schema = @Schema(implementation = UserDTO.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid input data.")})
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        log.info("Invoked on Thread: {}", Thread.currentThread().getName());
        userDTO.setRole(userDTO.getRole().toUpperCase());
        userDTO.setGender(userDTO.getGender().toUpperCase());
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}", ""})
    @Operation(summary = "Get user(s)", description = "Retrieves a user by ID or all users if no ID is provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<List<UserDTO>> getUsers(
            @Parameter(description = "ID of the user to retrieve. If not provided, all users will be retrieved.")
            @PathVariable(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(List.of(userService.getUserById(id)));
        } else {
            return ResponseEntity.ok(userService.getAllUsers());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Updates a user's details based on their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid input data."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "ID of the user to update.", required = true)
            @PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user based on their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public void deleteUser(
            @Parameter(description = "ID of the user to delete.", required = true)
            @PathVariable(value = "id") Long id) {
        log.info("Deleting user with Id: {}",id);
        userService.deleteUser(id);
    }
}