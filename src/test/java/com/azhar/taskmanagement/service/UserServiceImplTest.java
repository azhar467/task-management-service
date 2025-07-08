package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.dao.enums.Gender;
import com.azhar.taskmanagement.dao.enums.UserRole;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setRole(UserRole.ADMIN);
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));

        userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setRole("ADMIN");
        userDTO.setGender("MALE");
        userDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
    }

    @Test
    void saveUser_ShouldReturnSavedUserDTO() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList());
        when(taskRepository.findAll()).thenReturn(Arrays.asList());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.saveUser(userDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        userDTO.setName("Updated Name");
        userDTO.setTaskIds(Arrays.asList());
        userDTO.setProjectIds(Arrays.asList());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.updateUser(1L, userDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}