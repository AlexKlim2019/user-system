package com.klymenko.user.system.task.service.domain.handler.user;

import com.klymenko.user.system.task.service.domain.dto.command.user.SignInCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.AuthResponse;
import com.klymenko.user.system.task.service.domain.port.output.security.RequestAuthenticatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.klymenko.user.system.task.service.domain.utils.StringUtils.normalizeEmail;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticateUserCommandHandler {

    private final RequestAuthenticatorService requestAuthenticatorService;

    @Transactional
    public AuthResponse handle(SignInCommand command) {
        var normalizedEmail = normalizeEmail(command.email());
        return requestAuthenticatorService.authenticate(normalizedEmail, command.password());
    }
}
