package com.example.task.service;

import com.example.task.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTests {

    @Test
    void testAddTask() {
        TaskService taskService = new TaskService();
        Task task = taskService.addTask("Nouvelle tache");

        assertNotNull(task.getId(), "La tâche doit avoir un ID généré");
        assertEquals("Nouvelle tâche", task.getDescription());
        assertEquals("en cours", task.getStatus());
        assertTrue(taskService.getTasks().contains(task));
    }

    @Test
    void testCompleteTask_existingTask() {
        TaskService taskService = new TaskService();
        Task task = taskService.addTask("a compléter");

        boolean result = taskService.completeTask(task.getId());

        assertTrue(result, "La tâche doit être complétée avec succès");
        assertEquals("terminé", task.getStatus(), "Le statut doit être changé à 'terminé'");
    }

    @Test
    void testCompleteTask_nonExistentId() {
        TaskService taskService = new TaskService();
        boolean result = taskService.completeTask("id_inexistant");

        assertFalse(result, "La complétion d'un ID inexistant doit retourner false");
    }

    @Test
    void testDeleteTask_existingTask() {
        TaskService taskService = new TaskService();
        Task task = taskService.addTask("a supprimer");

        boolean result = taskService.deleteTask(task.getId());

        assertTrue(result, "La tache doit être supprimée avec succès");
        assertFalse(taskService.getTasks().contains(task), "La tache ne doit plus être dans la liste");
    }

    @Test
    void testDeleteTask_nonExistentId() {
        TaskService taskService = new TaskService();
        int sizeBefore = taskService.getTasks().size();

        boolean result = taskService.deleteTask("id_inexistant");

        assertFalse(result, "La suppression d'un ID inexistant doit retourner false");
        assertEquals(sizeBefore, taskService.getTasks().size(), "La taille de la liste doit rester identique");
    }

    @Test
    void testGetTasks_initialTasks() {
        TaskService taskService = new TaskService();
        List<Task> tasks = taskService.getTasks();

        assertEquals(2, tasks.size(), "Le service initialise deux taches par défaut");
    }
}
