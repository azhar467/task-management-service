package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.service.BaseService;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController extends BaseService {

    @Autowired
    protected UserService userService;

    @PostMapping("/addUser")
    @Operation(summary = "Create a new user", description = "Creates a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid input data.")})
    public ResponseEntity<CompletableFuture<UserDTO>> saveUser(
            @ParameterObject @RequestBody UserDTO userDTO) {
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
    public ResponseEntity<CompletableFuture<List<UserDTO>>> getUsers(
            @Parameter(description = "ID of the user to retrieve. If not provided, all users will be retrieved.")
            @PathVariable(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(userService.getUserById(id).thenApply(List::of));
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
    public ResponseEntity<CompletableFuture<UserDTO>> updateUser(
            @Parameter(description = "ID of the user to update.", required = true)
            @PathVariable Long id, @ParameterObject @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user based on their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<CompletableFuture<Void>> deleteUser(
            @Parameter(description = "ID of the user to delete.", required = true)
            @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}