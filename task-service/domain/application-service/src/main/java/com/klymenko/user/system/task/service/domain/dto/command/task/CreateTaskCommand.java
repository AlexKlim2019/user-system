package com.klymenko.user.system.task.service.domain.dto.command.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateTaskCommand(
        @NotBlank(message = "Title is mandatory!")
        String title,
        @NotBlank(message = "Description is mandatory!")
        String description
) {}
