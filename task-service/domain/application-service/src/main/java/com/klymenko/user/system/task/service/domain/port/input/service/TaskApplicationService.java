package com.klymenko.user.system.task.service.domain.port.input.service;

import com.klymenko.user.system.task.service.domain.dto.command.task.CreateTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.DeleteTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.PatchTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.query.FindTaskQuery;
import com.klymenko.user.system.task.service.domain.dto.query.SearchTasksQuery;
import com.klymenko.user.system.task.service.domain.dto.response.task.*;

public interface TaskApplicationService {

    CreateTaskResponse saveTask(CreateTaskCommand command);

    FindTaskResponse findTask(FindTaskQuery query);

    SearchTasksResponse searchTasks(SearchTasksQuery query);

    PatchTaskResponse partialUpdateTask(PatchTaskCommand command);

    DeleteTaskResponse deleteTask(DeleteTaskCommand command);
}
