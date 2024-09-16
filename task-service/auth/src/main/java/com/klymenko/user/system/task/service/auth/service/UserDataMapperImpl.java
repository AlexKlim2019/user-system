package com.klymenko.user.system.task.service.auth.service;

import com.klymenko.user.system.task.service.auth.jwt.JwtUser;
import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.model.UserView;
import com.klymenko.user.system.task.service.domain.entity.User;
import com.klymenko.user.system.task.service.domain.entity.UserRole;
import com.klymenko.user.system.task.service.domain.port.output.security.UserDataMapper;
import com.klymenko.user.system.task.service.domain.port.output.security.UserPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataMapperImpl implements UserDataMapper {
    private final UserPasswordEncoder encoder;

    public User createUserCommandToUser(SignUpCommand command, String normalizedEmail) {
        var passwordHash = encoder.encode(command.password());

        return User.builder()
                .email(normalizedEmail)
                .passwordHash(passwordHash)
                .firstName(command.firstName())
                .lastName(command.lastName())
                .email(command.email())
                .role(UserRole.USER)
                .build();
    }

    public UserView userToUserView(User user) {
        return UserView.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public UserView jwtUserToUserView(JwtUser user) {
        return UserView.builder()
                .id(user.getId())
                .email(user.getUsername())
                .firstName(user.getFistName())
                .lastName(user.getLastName())
                .build();
    }
}
