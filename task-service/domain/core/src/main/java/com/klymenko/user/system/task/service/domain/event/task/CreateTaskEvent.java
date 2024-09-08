package com.klymenko.user.system.task.service.domain.event.task;


import com.klymenko.user.system.task.service.domain.entity.Task;

import java.time.LocalDateTime;

public class CreateTaskEvent extends TaskEvent{
    public CreateTaskEvent(Task task, LocalDateTime createdAt) {
        super(task, createdAt);
    }
}
