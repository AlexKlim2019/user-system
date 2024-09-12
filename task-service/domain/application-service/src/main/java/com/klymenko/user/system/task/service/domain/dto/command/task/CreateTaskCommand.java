package com.klymenko.user.system.task.service.domain.dto.command.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateTaskCommand(
        @NotBlank(message = "Title is mandatory!")
        String title,
        @NotBlank(message = "Description is mandatory!")
        String description,
        @NotNull(message = "User id is mandatory!")
        UUID userId
) {}
