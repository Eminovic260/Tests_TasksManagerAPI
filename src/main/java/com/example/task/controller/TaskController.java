package com.example.task.controller;
import com.example.task.model.Task;
import java.util.List;

import com.example.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> routeFind (){
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to the Task Manager API!");
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task.getDescription());
        return ResponseEntity.ok(newTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        boolean deleted = taskService.deleteTask(taskId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable String taskId) {
        boolean success = taskService.completeTask(taskId);
        if (success) {
            // Retourner la tâche mise à jour
            Task updatedTask = taskService.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build(); // id non trouvé
        }
    }


}
