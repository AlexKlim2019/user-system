package com.klymenko.user.system.task.service.domain.dto.response.task;


import com.klymenko.user.system.task.service.domain.entity.Task;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record PatchTaskResponse(@NonNull Task task, String message) {}
