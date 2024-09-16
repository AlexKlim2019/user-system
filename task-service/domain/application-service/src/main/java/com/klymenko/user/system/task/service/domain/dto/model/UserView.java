package com.klymenko.user.system.task.service.domain.dto.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserView(UUID id, String email, String firstName, String lastName) {}
