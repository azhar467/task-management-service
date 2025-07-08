package com.azhar.taskmanagement.controller.view;

import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class UserViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void listUsers_ShouldReturnUserListView() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO(1L, "John", "MALE", LocalDate.of(1990, 1, 1), "john@example.com", "ADMIN", null, null, null, null),
                new UserDTO(2L, "Jane", "FEMALE", LocalDate.of(1992, 2, 2), "jane@example.com", "USER", null, null, null, null)
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/user-list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void viewUser_ShouldReturnUserDetailView() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/user-detail"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void showUserForm_ShouldReturnUserFormView() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/user-form"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void saveUser_ShouldRedirectToUsersList() throws Exception {
        UserDTO savedUser = new UserDTO();
        savedUser.setId(1L);

        when(userService.saveUser(any(UserDTO.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users/save")
                .param("name", "John Doe")
                .param("email", "john@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void editUser_ShouldReturnUserFormView() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/user-form"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void updateUser_ShouldRedirectToUsersList() throws Exception {
        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(1L);

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(post("/users/update/1")
                .param("name", "Updated Name")
                .param("email", "updated@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void deleteUser_ShouldRedirectToUsersList() throws Exception {
        mockMvc.perform(get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}