package com.klymenko.user.system.task.service.domain.exception;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
