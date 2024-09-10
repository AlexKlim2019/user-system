package com.klymenko.user.system.task.service.domain.dto.query;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SearchTasksQuery(
        @NotNull(message = "User id is mandatory")
        UUID userId
) {}
