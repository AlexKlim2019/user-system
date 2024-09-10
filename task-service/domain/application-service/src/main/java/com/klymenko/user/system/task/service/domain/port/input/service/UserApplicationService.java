package com.klymenko.user.system.task.service.domain.port.input.service;

import com.klymenko.user.system.task.service.domain.dto.command.user.CreateUserCommand;
import com.klymenko.user.system.task.service.domain.dto.query.FindUserQuery;
import com.klymenko.user.system.task.service.domain.dto.response.user.CreateUserResponse;
import com.klymenko.user.system.task.service.domain.dto.response.user.FindUserResponse;

public interface UserApplicationService {

    CreateUserResponse saveUser(CreateUserCommand command);

    FindUserResponse findUser(FindUserQuery query);
}
