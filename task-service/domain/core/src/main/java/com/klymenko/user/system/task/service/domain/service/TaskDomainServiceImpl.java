package com.klymenko.user.system.task.service.domain.service;

import com.klymenko.user.system.task.service.domain.entity.Task;
import com.klymenko.user.system.task.service.domain.event.task.CreateTaskEvent;
import com.klymenko.user.system.task.service.domain.event.task.ValidateTaskEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class TaskDomainServiceImpl implements TaskDomainService {
    @Override
    public CreateTaskEvent validateAndInitiateTask(Task task) {
        validateTask(task);
        task.initializeTask();
        log.info("Task with id: {} is initiated", task.getId());
        return new CreateTaskEvent(task, LocalDateTime.now());
    }

    @Override
    public ValidateTaskEvent validateTask(Task task) {
        log.info("Task with id: {} is validated successful", task.getId());
        return new ValidateTaskEvent(task, LocalDateTime.now());
    }
}
