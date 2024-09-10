package com.klymenko.user.system.task.service.domain.handler.user;

import com.klymenko.user.system.task.service.domain.dto.command.user.CreateUserCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.CreateUserResponse;
import com.klymenko.user.system.task.service.domain.entity.User;
import com.klymenko.user.system.task.service.domain.event.user.CreateUserEvent;
import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import com.klymenko.user.system.task.service.domain.mapper.UserDataMapper;
import com.klymenko.user.system.task.service.domain.port.output.repository.UserRepository;
import com.klymenko.user.system.task.service.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler {

    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final UserDomainService userDomainService;

    @Transactional
    public CreateUserResponse handle(CreateUserCommand command) {
        User user = userDataMapper.createUserCommandToUser(command);
        CreateUserEvent event = userDomainService.validateAndInitiateUser(user);
        var result = userRepository.save(event.getUser(), event.getCreatedAt());
        if (result == null) {
            throw new UserDomainException("Could not save user!");
        }
        return new CreateUserResponse(event.getUser(), "User with has been created successfully");
    }
}
