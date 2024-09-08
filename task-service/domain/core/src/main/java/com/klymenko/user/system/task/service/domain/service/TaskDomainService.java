package com.klymenko.user.system.task.service.domain.service;


import com.klymenko.user.system.task.service.domain.entity.Task;
import com.klymenko.user.system.task.service.domain.event.task.CreateTaskEvent;
import com.klymenko.user.system.task.service.domain.event.task.ValidateTaskEvent;

public interface TaskDomainService {
    CreateTaskEvent validateAndInitiateTask(Task task);

    ValidateTaskEvent validateTask(Task task);
}
