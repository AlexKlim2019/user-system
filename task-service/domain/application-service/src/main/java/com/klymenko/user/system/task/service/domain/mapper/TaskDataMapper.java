package com.klymenko.user.system.task.service.domain.mapper;

import com.klymenko.user.system.task.service.domain.dto.command.task.CreateTaskCommand;
import com.klymenko.user.system.task.service.domain.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskDataMapper {

    public Task createTaskCommandToTask(CreateTaskCommand command) {
        return Task.builder()
                .title(command.title())
                .description(command.description())
                .build();
    }
}
