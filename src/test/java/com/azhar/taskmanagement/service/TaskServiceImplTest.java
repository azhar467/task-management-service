package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.dao.Project;
import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.dto.TaskDTO;
import com.azhar.taskmanagement.dao.enums.TaskPriority;
import com.azhar.taskmanagement.dao.enums.TaskStatus;
import com.azhar.taskmanagement.exception.EntityNotFoundException;
import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import com.azhar.taskmanagement.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDTO taskDTO;
    private User user;
    private Project project;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);
        task.setAssignee(user);
        task.setProject(project);

        taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        taskDTO.setStatus("TODO");
        taskDTO.setPriority("HIGH");
        taskDTO.setAssigneeId(1L);
        taskDTO.setProjectId(1L);
        taskDTO.setDueDate(LocalDateTime.now().plusDays(7));
    }

    @Test
    void saveTask_ShouldReturnSavedTaskDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(userRepository.save(any(User.class))).thenReturn(user);

        TaskDTO result = taskService.saveTask(taskDTO);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<TaskDTO> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        taskDTO.setTitle("Updated Task");
        taskDTO.setAssigneeName(null);
        taskDTO.setProjectName(null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.updateTask(1L, taskDTO);

        assertNotNull(result);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deleteTask_ShouldCallRepository() {
        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }
}