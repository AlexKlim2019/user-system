package com.klymenko.user.system.task.service.domain.dto.response.user;


import com.klymenko.user.system.task.service.domain.entity.User;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record FindUserResponse(@NonNull User user, String message) {}
