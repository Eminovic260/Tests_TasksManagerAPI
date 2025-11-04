package com.example.task.model;
import java.util.UUID;


public class Task {

    private final String id;
    private String description;
    private String status;

    public Task(String id, String description, String status) {
        this.id = id;
        this.description = description;
        this.status = "en cours";
    }

    public String getId() {return id;}
    public String getDescription() {return description;}
    public String getStatus() {return status;}

    public void setDescription(String description) {this.description = description;}
    public void setStatus(String status) {this.status = status;}





}
