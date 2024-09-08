package com.klymenko.user.system.task.service.domain.event.user;


import com.klymenko.user.system.task.service.domain.entity.User;
import java.time.LocalDateTime;

public abstract class UserEvent {
    private final User user;

    private final LocalDateTime createdAt;

    public UserEvent(User user, LocalDateTime createdAt) {
        this.user = user;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
