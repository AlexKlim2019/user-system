package com.klymenko.user.system.task.service.domain.handler.task;

import com.klymenko.user.system.task.service.domain.dto.command.task.CreateTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.response.task.CreateTaskResponse;
import com.klymenko.user.system.task.service.domain.entity.Task;
import com.klymenko.user.system.task.service.domain.event.task.CreateTaskEvent;
import com.klymenko.user.system.task.service.domain.exception.TaskDomainException;
import com.klymenko.user.system.task.service.domain.mapper.TaskDataMapper;
import com.klymenko.user.system.task.service.domain.port.output.repository.TaskRepository;
import com.klymenko.user.system.task.service.domain.service.TaskDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTaskCommandHandler {

    private final TaskRepository repository;
    private final TaskDataMapper mapper;
    private final TaskDomainService domainService;

    @Transactional
    public CreateTaskResponse handle(CreateTaskCommand command) {
        Task task = mapper.createTaskCommandToTask(command);
        CreateTaskEvent event = domainService.validateAndInitiateTask(task);
        var result = repository.save(event.getTask(), event.getCreatedAt());
        if (result == null) {
            throw new TaskDomainException("Could not save task!");
        }
        return new CreateTaskResponse(event.getTask(), "Task has been created successfully");
    }
}
