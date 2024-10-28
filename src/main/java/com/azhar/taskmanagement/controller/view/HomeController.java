package com.azhar.taskmanagement.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/task-management-service")
    public String home() {
        return "home"; // This will map to home.html
    }
}
