package com.klymenko.user.system.task.service.api.utils;


import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.model.UserView;
import com.klymenko.user.system.task.service.domain.dto.response.user.RegisterUserResponse;

import static com.klymenko.user.system.task.service.api.utils.TestConstants.*;

public class UserGenerator {

    public static UserView generateValidUserView() {
        return UserView.builder()
                .id(USER_ID)
                .email(VALID_EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static class CommandGenerator {
        public static SignUpCommand generateValidSignUpUserCommand() {
            return SignUpCommand.builder()
                    .email(VALID_EMAIL)
                    .password(VALID_PASSWORD)
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .build();
        }
    }


    public static class Responses {
        public static RegisterUserResponse generateSuccessSignUpUserResponse() {
            return RegisterUserResponse.builder()
                    .user(generateValidUserView())
                    .message("Create user response message")
                    .build();
        }
    }
}
