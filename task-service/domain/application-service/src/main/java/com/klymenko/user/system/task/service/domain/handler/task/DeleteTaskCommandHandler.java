package com.klymenko.user.system.task.service.domain.handler.task;


import com.klymenko.user.system.task.service.domain.dto.command.task.DeleteTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.response.task.DeleteTaskResponse;
import com.klymenko.user.system.task.service.domain.exception.TaskNotFoundException;
import com.klymenko.user.system.task.service.domain.port.output.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.klymenko.user.system.task.service.domain.utils.StringUtils.concatenate;


@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteTaskCommandHandler {

    private final TaskRepository repository;

    @Transactional
    public DeleteTaskResponse handle(DeleteTaskCommand command) {
        repository.findById(command.id())
                .orElseThrow(() -> new TaskNotFoundException("Task not found with given id!"));
        repository.deleteById(command.id());
        log.info("Task with id {} has been deleted successfully", command.id());
        var responseMessage = concatenate("Task with id", command.id().toString(), "has been deleted successfully");
        return new DeleteTaskResponse(responseMessage);
    }
}
