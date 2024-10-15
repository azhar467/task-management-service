package com.azhar.taskmanagement.service;

import com.azhar.taskmanagement.repository.ProjectRepository;
import com.azhar.taskmanagement.repository.TaskRepository;
import com.azhar.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    @Autowired protected ProjectRepository projectRepository;
    @Autowired protected TaskRepository taskRepository;
    @Autowired protected UserRepository userRepository;
}
