package com.klymenko.user.system.task.service.dao.mapper;

import com.klymenko.user.system.task.service.dao.entity.TaskEntity;
import com.klymenko.user.system.task.service.domain.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskDaoMapper {

    public TaskEntity taskToTaskEntity(Task task, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return TaskEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public Task taskEntityToTask(TaskEntity entity) {
        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }
}
