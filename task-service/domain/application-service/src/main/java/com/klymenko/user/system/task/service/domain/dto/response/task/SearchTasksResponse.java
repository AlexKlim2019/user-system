package com.klymenko.user.system.task.service.domain.dto.response.task;

import com.klymenko.user.system.task.service.domain.entity.Task;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchTasksResponse(List<Task> tasks, String message) {}
