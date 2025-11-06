package com.example.task.controller;

import com.example.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void clean() {
        taskService.getTasks().clear();
    }

    @Test
    void getTasks_should_return_tasks_from_real_service() throws Exception {
        taskService.addTask("Tâche intégration");

        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].description").value("Tache intégration"));
    }

    @Test
    void getTasks_should_return_empty_list_when_no_tasks() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

    @Test
    void hello_should_return_message() throws Exception {
        mockMvc.perform(get("/tasks/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Welcome to the Task Manager API!"));
    }
}
