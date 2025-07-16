package com.azhar.taskmanagement.controller;

import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@WebMvcTest(UserController.class)
@ActiveProfiles("local")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void saveUser_ShouldReturnCreatedUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setRole("ADMIN");
        userDTO.setGender("MALE");
        userDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));

        UserDTO savedUser = new UserDTO();
        savedUser.setId(1L);
        savedUser.setName("John Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setRole("ADMIN");
        savedUser.setGender("MALE");

        when(userService.saveUser(any(UserDTO.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getUserById_ShouldReturnSingleUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO(1L, "John", "MALE", LocalDate.of(1990, 1, 1), "john@example.com", "ADMIN", null, null, null, null,null,null),
                new UserDTO(2L, "Jane", "FEMALE", LocalDate.of(1992, 2, 2), "jane@example.com", "USER", null, null, null, null,null, null)
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Updated Name");
        userDTO.setEmail("updated@example.com");

        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(1L);
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}