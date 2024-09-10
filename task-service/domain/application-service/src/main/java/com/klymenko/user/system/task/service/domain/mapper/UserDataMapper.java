package com.klymenko.user.system.task.service.domain.mapper;

import com.klymenko.user.system.task.service.domain.dto.command.user.CreateUserCommand;
import com.klymenko.user.system.task.service.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {

    public User createUserCommandToUser(CreateUserCommand command) {
        return User.builder()
                .email(command.email())
                .password(command.password())
                .firstName(command.firstName())
                .lastName(command.lastName())
                .email(command.email())
                .build();
    }
}
