package com.klymenko.user.system.task.service.domain.dto.command.task;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PatchTaskCommand(
        @NotNull(message = "Id is mandatory!")
        UUID id,
        String title,
        String description
) {}
