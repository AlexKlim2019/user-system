package com.klymenko.user.system.task.service.domain.dto.response.user;


import com.klymenko.user.system.task.service.domain.dto.model.UserView;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RegisterUserResponse(@NonNull UserView user, String message) {}
