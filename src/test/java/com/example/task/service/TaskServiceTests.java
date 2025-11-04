package com.example.task.service;

import com.example.task.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTests {

    @Test
    void testAddTask() {
        TaskService taskService = new TaskService();

        Task task = taskService.addTask("premiere tache");

        assertNotNull(task.getId());
        assertEquals("premiere tache", task.getDescription());
        assertEquals("en cours", task.getStatus());
        assertTrue(taskService.getTasks().contains(task));
    }

    @Test
    void testCompleteTask() {
        TaskService taskService = new TaskService();
        Task task = taskService.addTask("en cours");

        assertTrue(taskService.completeTask(task.getId()));
        assertEquals("terminé", task.getStatus());
    }

    @Test
    void testDeleteTask() {
        TaskService taskService = new TaskService();
        Task task = taskService.addTask("tache a supprimer");

        assertTrue(taskService.deleteTask(task.getId()));
        assertFalse(taskService.getTasks().contains(task));
    }
}
