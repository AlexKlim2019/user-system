package com.klymenko.user.system.task.service.domain.service;

import com.klymenko.user.system.task.service.domain.entity.User;
import com.klymenko.user.system.task.service.domain.event.user.CreateUserEvent;


public interface UserDomainService {
    CreateUserEvent validateAndInitiateUser(User user);

    void validateUser(User user);
}
