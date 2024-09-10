package com.klymenko.user.system.task.service.domain.handler.user;

import com.klymenko.user.system.task.service.domain.dto.query.FindUserQuery;
import com.klymenko.user.system.task.service.domain.dto.response.user.FindUserResponse;
import com.klymenko.user.system.task.service.domain.exception.UserNotFoundException;
import com.klymenko.user.system.task.service.domain.port.output.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindUserQueryHandler {

    private final UserRepository userRepository;

    @Transactional
    public FindUserResponse handle(FindUserQuery query) {
        var user = userRepository.findById(query.id())
                .orElseThrow(() -> new UserNotFoundException("User not found with given id!"));
        return new FindUserResponse(user, "User has been found successfully");
    }
}
