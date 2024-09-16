package com.klymenko.user.system.task.service.domain.port.input.service.impl;

import com.klymenko.user.system.task.service.domain.dto.command.user.SignInCommand;
import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.AuthResponse;
import com.klymenko.user.system.task.service.domain.dto.response.user.RegisterUserResponse;
import com.klymenko.user.system.task.service.domain.handler.user.AuthenticateUserCommandHandler;
import com.klymenko.user.system.task.service.domain.handler.user.RegisterUserCommandHandler;
import com.klymenko.user.system.task.service.domain.port.input.service.AuthApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthApplicationServiceImpl implements AuthApplicationService {

    private final RegisterUserCommandHandler registerUserCommandHandler;
    private final AuthenticateUserCommandHandler authenticateUserCommandHandler;

    @Override
    public RegisterUserResponse saveUser(SignUpCommand command) {
        return registerUserCommandHandler.handle(command);
    }

    @Override
    public AuthResponse authenticateUser(SignInCommand command) {
        return authenticateUserCommandHandler.handle(command);
    }
}
