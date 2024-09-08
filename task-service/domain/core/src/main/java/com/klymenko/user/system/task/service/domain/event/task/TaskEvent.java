package com.klymenko.user.system.task.service.domain.event.task;


import com.klymenko.user.system.task.service.domain.entity.Task;

import java.time.LocalDateTime;

public abstract class TaskEvent {
    private final Task task;

    private final LocalDateTime createdAt;

    public TaskEvent(Task task, LocalDateTime createdAt) {
        this.task = task;
        this.createdAt = createdAt;
    }

    public Task getTask() {
        return task;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
