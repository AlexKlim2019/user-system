package com.klymenko.user.system.task.service.domain.event.user;


import com.klymenko.user.system.task.service.domain.entity.User;

import java.time.LocalDateTime;

public class CreateUserEvent extends UserEvent{
    public CreateUserEvent(User user, LocalDateTime createdAt) {
        super(user, createdAt);
    }
}
