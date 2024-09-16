package com.klymenko.user.system.task.service.domain.port.output.security;

import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.model.UserView;
import com.klymenko.user.system.task.service.domain.entity.User;

public interface UserDataMapper {

    User createUserCommandToUser(SignUpCommand command, String normalizedEmail);

    UserView userToUserView(User user);
}
