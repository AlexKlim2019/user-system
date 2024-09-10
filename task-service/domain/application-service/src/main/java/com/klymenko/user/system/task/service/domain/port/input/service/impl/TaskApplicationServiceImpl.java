package com.klymenko.user.system.task.service.domain.port.input.service.impl;

import com.klymenko.user.system.task.service.domain.dto.command.task.CreateTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.DeleteTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.PatchTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.query.FindTaskQuery;
import com.klymenko.user.system.task.service.domain.dto.query.SearchTasksQuery;
import com.klymenko.user.system.task.service.domain.dto.response.task.*;
import com.klymenko.user.system.task.service.domain.handler.task.*;
import com.klymenko.user.system.task.service.domain.port.input.service.TaskApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskApplicationServiceImpl implements TaskApplicationService {
    private final CreateTaskCommandHandler createTaskCommandHandler;
    private final FindTaskQueryHandler findTaskQueryHandler;
    private final SearchTasksQueryHandler searchTasksQueryHandler;
    private final PatchTaskCommandHandler patchTaskCommandHandler;
    private final DeleteTaskCommandHandler deleteTaskCommandHandler;

    @Override
    public CreateTaskResponse saveTask(CreateTaskCommand command) {
        return createTaskCommandHandler.handle(command);
    }

    @Override
    public FindTaskResponse findTask(FindTaskQuery query) {
        return findTaskQueryHandler.handle(query);
    }

    @Override
    public SearchTasksResponse searchTasks(SearchTasksQuery query) {
        return searchTasksQueryHandler.handle(query);
    }

    @Override
    public PatchTaskResponse partialUpdateTask(PatchTaskCommand command) {
        return patchTaskCommandHandler.handle(command);
    }

    @Override
    public DeleteTaskResponse deleteTask(DeleteTaskCommand command) {
        return deleteTaskCommandHandler.handle(command);
    }
}
