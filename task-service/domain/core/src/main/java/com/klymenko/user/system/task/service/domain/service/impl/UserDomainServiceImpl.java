package com.klymenko.user.system.task.service.domain.service.impl;

import com.klymenko.user.system.task.service.domain.entity.User;
import com.klymenko.user.system.task.service.domain.event.user.CreateUserEvent;
import com.klymenko.user.system.task.service.domain.event.user.ValidateUserEvent;
import com.klymenko.user.system.task.service.domain.service.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Slf4j
public class UserDomainServiceImpl implements UserDomainService {

    public UserDomainServiceImpl() {
    }

    @Override
    public CreateUserEvent validateAndInitiateUser(User user) {
        validateUser(user);
        user.initializeUser();
        log.info("User with id: {} is initiated", user.getId());
        return new CreateUserEvent(user, LocalDateTime.now());
    }

    @Override
    public ValidateUserEvent validateUser(User user) {
        log.info("User with id: {} is validated successful", user.getId());
        return new ValidateUserEvent(user, LocalDateTime.now());
    }
}
