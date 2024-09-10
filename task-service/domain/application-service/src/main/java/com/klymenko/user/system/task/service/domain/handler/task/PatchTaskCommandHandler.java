package com.klymenko.user.system.task.service.domain.handler.task;

import com.klymenko.user.system.task.service.domain.dto.command.task.PatchTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.response.task.PatchTaskResponse;
import com.klymenko.user.system.task.service.domain.entity.Task;
import com.klymenko.user.system.task.service.domain.event.task.ValidateTaskEvent;
import com.klymenko.user.system.task.service.domain.exception.UserNotFoundException;
import com.klymenko.user.system.task.service.domain.port.output.repository.TaskRepository;
import com.klymenko.user.system.task.service.domain.service.TaskDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatchTaskCommandHandler {

    private final TaskRepository repository;
    private final TaskDomainService domainService;

    @Transactional
    public PatchTaskResponse handle(PatchTaskCommand command) {
        var task = repository.findById(command.id())
                .orElseThrow(() -> new UserNotFoundException("Task not found with given id!"));
        var updatedTask = partialUpdateTask(task, command);
        ValidateTaskEvent event = domainService.validateTask(updatedTask);
        repository.update(updatedTask, event.getCreatedAt());
        return new PatchTaskResponse(event.getTask(), "Task has been updated partially and successfully");
    }

    private Task partialUpdateTask(Task task, PatchTaskCommand command) {
        if (command.title() != null) {
            task.setTitle(command.title());
        }
        if (command.description() != null) {
            task.setDescription(command.description());
        }
        return task;
    }
}
