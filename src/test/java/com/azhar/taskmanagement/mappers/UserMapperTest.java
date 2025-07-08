package com.azhar.taskmanagement.mappers;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.UserDTO;
import com.azhar.taskmanagement.dao.enums.Gender;
import com.azhar.taskmanagement.dao.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toDto_ShouldConvertUserToUserDTO() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setRole(UserRole.ADMIN);
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        Project project = new Project();
        project.setId(1L);
        user.setProjects(Arrays.asList(project));

        Task task = new Task();
        task.setId(1L);
        user.setTasks(Arrays.asList(task));

        UserDTO result = UserMapper.toDto(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("ADMIN", result.getRole());
        assertEquals("MALE", result.getGender());
        assertEquals(1, result.getProjectIds().size());
        assertEquals(1, result.getTaskIds().size());
    }

    @Test
    void toDto_ShouldReturnNull_WhenUserIsNull() {
        UserDTO result = UserMapper.toDto(null);
        assertNull(result);
    }

    @Test
    void toEntity_ShouldConvertUserDTOToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setRole("ADMIN");
        userDTO.setGender("MALE");
        userDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userDTO.setProjectIds(Arrays.asList(1L));
        userDTO.setTaskIds(Arrays.asList(1L));

        Project project = new Project();
        project.setId(1L);
        List<Project> projects = Arrays.asList(project);

        Task task = new Task();
        task.setId(1L);
        List<Task> tasks = Arrays.asList(task);

        User result = UserMapper.toEntity(userDTO, projects, tasks);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(UserRole.ADMIN, result.getRole());
        assertEquals(Gender.MALE, result.getGender());
        assertEquals(1, result.getProjects().size());
        assertEquals(1, result.getTasks().size());
    }

    @Test
    void toEntity_ShouldReturnNull_WhenUserDTOIsNull() {
        User result = UserMapper.toEntity(null, null, null);
        assertNull(result);
    }

    @Test
    void constructor_ShouldThrowException() {
        assertThrows(IllegalStateException.class, () -> {
            new UserMapper();
        });
    }
}