package ua.org.shpp.todolist.dto;

import ua.org.shpp.todolist.enums.Status;

import java.time.LocalDateTime;

public class TaskDTO {

    private Long id;
    private Status status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;

    public TaskDTO() {
    }

    public TaskDTO(Long id, Status status, String description, LocalDateTime deadline) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
