package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setup() {
        task1 = new Task("Tache 1");
        task2 = new Task("Tache 2");
    }

    // ------------------- GET /tasks -------------------
    @Test
    void getTasks_should_return_list_of_tasks() throws Exception {
        when(taskService.getTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].description").value("Tache 1"))
            .andExpect(jsonPath("$[1].description").value("Tache 2"));
    }

    @Test
    void getTasks_should_return_empty_list_when_no_tasks() throws Exception {
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

    @Test
    void createTask_should_return_created_task() throws Exception {
        Task newTask = new Task("Nouvelle Tâche");
        when(taskService.addTask("Nouvelle Tâche")).thenReturn(newTask);

        mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .content("{\"description\":\"Nouvelle Tâche\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Nouvelle Tâche"));
    }

    @Test
    void deleteTask_should_return_no_content_when_success() throws Exception {
        when(taskService.deleteTask(task1.getId())).thenReturn(true);

        mockMvc.perform(delete("/tasks/" + task1.getId()))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_should_return_not_found_when_fail() throws Exception {
        when(taskService.deleteTask("invalid-id")).thenReturn(false);

        mockMvc.perform(delete("/tasks/invalid-id"))
            .andExpect(status().isNotFound());
    }

    @Test
    void completeTask_should_return_updated_task_when_success() throws Exception {
        task1.setStatus("terminé");
        when(taskService.completeTask(task1.getId())).thenReturn(true);
        when(taskService.getTasks()).thenReturn(List.of(task1));

        mockMvc.perform(put("/tasks/" + task1.getId() + "/complete"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("terminé"));
    }

    @Test
    void completeTask_should_return_not_found_when_fail() throws Exception {
        when(taskService.completeTask("invalid-id")).thenReturn(false);

        mockMvc.perform(put("/tasks/invalid-id/complete"))
            .andExpect(status().isNotFound());
    }
}
