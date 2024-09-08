package com.klymenko.user.system.task.service.domain.event.task;

import com.klymenko.user.system.task.service.domain.entity.Task;

import java.time.LocalDateTime;

public class ValidateTaskEvent extends TaskEvent{
    public ValidateTaskEvent(Task task, LocalDateTime createdAt) {
        super(task, createdAt);
    }
}
