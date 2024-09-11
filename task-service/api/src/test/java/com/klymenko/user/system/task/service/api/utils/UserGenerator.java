package com.klymenko.user.system.task.service.api.utils;


import com.klymenko.user.system.task.service.domain.dto.command.user.CreateUserCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.CreateUserResponse;
import com.klymenko.user.system.task.service.domain.entity.User;

import static com.klymenko.user.system.task.service.api.utils.TestConstants.*;

public class UserGenerator {
    public static User generateValidUser() {
        return User.builder()
                .id(USER_ID)
                .email(VALID_EMAIL)
                .password(VALID_PASSWORD)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static class CommandGenerator {
        public static CreateUserCommand generateValidCreateUserCommand() {
            return CreateUserCommand.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .build();
        }
    }


    public static class Responses {
        public static CreateUserResponse generateSuccessCreateUserResponse() {
            return CreateUserResponse.builder()
                    .user(generateValidUser())
                    .message("Create user response message")
                    .build();
        }
    }
}
