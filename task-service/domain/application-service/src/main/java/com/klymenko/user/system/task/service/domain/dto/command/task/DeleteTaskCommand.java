package com.klymenko.user.system.task.service.domain.dto.command.task;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteTaskCommand(@NotNull(message = "Id is mandatory!") UUID id) {}
