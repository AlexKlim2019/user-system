package com.klymenko.user.system.task.service.domain.handler.user;

import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.RegisterUserResponse;
import com.klymenko.user.system.task.service.domain.entity.User;
import com.klymenko.user.system.task.service.domain.event.user.CreateUserEvent;
import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import com.klymenko.user.system.task.service.domain.port.output.repository.UserRepository;
import com.klymenko.user.system.task.service.domain.port.output.security.UserDataMapper;
import com.klymenko.user.system.task.service.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.klymenko.user.system.task.service.domain.utils.StringUtils.concatenate;
import static com.klymenko.user.system.task.service.domain.utils.StringUtils.normalizeEmail;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterUserCommandHandler {

    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final UserDomainService userDomainService;

    @Transactional
    public RegisterUserResponse handle(SignUpCommand command) {
        var normalizedEmail = normalizeEmail(command.email());
        var existedUser = userRepository.findByEmail(normalizedEmail);
        if (existedUser.isPresent()) {
            throw new UserDomainException(concatenate("User with email ", command.email(), " already exists"));
        }
        User user = userDataMapper.createUserCommandToUser(command, normalizedEmail);
        CreateUserEvent event = userDomainService.validateAndInitiateUser(user);
        var result = userRepository.save(event.getUser(), event.getCreatedAt());
        if (result == null) {
            throw new UserDomainException("Could not save user!");
        }
        var userView = userDataMapper.userToUserView(user);
        return new RegisterUserResponse(userView, "User with has been created successfully");
    }
}
