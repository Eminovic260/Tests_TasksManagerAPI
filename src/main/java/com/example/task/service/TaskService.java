package com.example.task.service;

import com.example.task.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private List<Task> tasks = new ArrayList<>();

  /*  public TaskService() {
        addTask("Tâche 1");
        addTask("Tâche 2");
    }*/
    public Task addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        return task;
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public boolean deleteTask(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                tasks.remove(task);
                return true;

            }
        }
        return false;
    }

        public boolean completeTask(String id){
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus("terminé");
                return true;
            }
        }
            return false;

        }
    }
