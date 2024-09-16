package com.klymenko.user.system.task.service.domain.port.input.service;

import com.klymenko.user.system.task.service.domain.dto.command.user.SignInCommand;
import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.AuthResponse;
import com.klymenko.user.system.task.service.domain.dto.response.user.RegisterUserResponse;

public interface AuthApplicationService {

    RegisterUserResponse saveUser(SignUpCommand command);

    AuthResponse authenticateUser(SignInCommand command);
}
