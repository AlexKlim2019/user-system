package com.klymenko.user.system.task.service.domain.dto.query;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FindUserQuery(
        @NotNull(message = "Id is mandatory!")
        UUID id
) {}
