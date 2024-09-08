package com.klymenko.user.system.task.service.domain.service;


import com.klymenko.user.system.task.service.domain.event.user.CreateUserEvent;
import com.klymenko.user.system.task.service.domain.event.user.ValidateUserEvent;
import com.klymenko.user.system.task.service.domain.entity.User;

public interface UserDomainService {
    CreateUserEvent validateAndInitiateUser(User user);

    ValidateUserEvent validateUser(User user);
}
