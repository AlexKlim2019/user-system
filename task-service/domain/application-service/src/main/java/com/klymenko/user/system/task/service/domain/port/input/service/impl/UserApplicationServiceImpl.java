package com.klymenko.user.system.task.service.domain.port.input.service.impl;

import com.klymenko.user.system.task.service.domain.dto.command.user.CreateUserCommand;
import com.klymenko.user.system.task.service.domain.dto.query.FindUserQuery;
import com.klymenko.user.system.task.service.domain.dto.response.user.CreateUserResponse;
import com.klymenko.user.system.task.service.domain.dto.response.user.FindUserResponse;
import com.klymenko.user.system.task.service.domain.handler.user.CreateUserCommandHandler;
import com.klymenko.user.system.task.service.domain.handler.user.FindUserQueryHandler;
import com.klymenko.user.system.task.service.domain.port.input.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final CreateUserCommandHandler createUserCommandHandler;
    private final FindUserQueryHandler findUserQueryHandler;

    @Override
    public CreateUserResponse saveUser(CreateUserCommand command) {
        return createUserCommandHandler.handle(command);
    }

    @Override
    public FindUserResponse findUser(FindUserQuery query) {
        return findUserQueryHandler.handle(query);
    }
}
