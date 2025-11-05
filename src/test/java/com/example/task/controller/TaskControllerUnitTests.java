package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerUnitTests {

    @Autowired
    private MockMvc mockMvc; //Object used to make HTTP request on our API

    @MockitoBean
    private TaskService taskService; //Mock object TaskService automatically injected in TaskController instance

    @BeforeEach
    void setup() {
        List<Task> tasks = List.of(new Task("Tache 1"));
        when(taskService.getTasks()).thenReturn(tasks);
    }

    @Test
    void getTasks_should_return_list_of_tasks() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].description").value("Tache 1"));
    }

    @Test
    void sould_return_empty_list_when_no_tasks() throws Exception {
        when(taskService.getTasks()).thenReturn(List.of());
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
