package com.klymenko.user.system.task.service.domain.handler.task;

import com.klymenko.user.system.task.service.domain.dto.query.FindTaskQuery;
import com.klymenko.user.system.task.service.domain.dto.response.task.FindTaskResponse;
import com.klymenko.user.system.task.service.domain.exception.TaskNotFoundException;
import com.klymenko.user.system.task.service.domain.port.output.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindTaskQueryHandler {

    private final TaskRepository repository;

    public FindTaskResponse handle(FindTaskQuery query) {
        var task = repository.findById(query.id())
                .orElseThrow(() -> new TaskNotFoundException("Task not found with given id!"));
        return new FindTaskResponse(task, "Task has been found successfully");
    }
}
