package com.example.taskmaster.dto.task;

import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskStatus status;

    private Long assignedToId;
    private String assignedToName;

    private Long createdById;
    private String createdByName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskResponse(Task task) {

        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();

        if (task.getAssignedTo() != null) {
            this.assignedToId = task.getAssignedTo().getId();
            this.assignedToName = task.getAssignedTo().getName();
        }

        this.createdById = task.getCreatedBy().getId();
        this.createdByName = task.getCreatedBy().getName();

        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}