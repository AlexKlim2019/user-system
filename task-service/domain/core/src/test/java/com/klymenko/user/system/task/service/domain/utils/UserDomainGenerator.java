package com.klymenko.user.system.task.service.domain.utils;


import com.klymenko.user.system.task.service.domain.entity.User;

public class UserDomainGenerator {

    private static final String DEFAULT_EMAIL = "test@mail.com";
    private static final String DEFAULT_PASSWORD = "Test password";
    private static final String DEFAULT_FIRST_NAME = "Test first name";
    private static final String DEFAULT_LAST_NAME = "Test last name";

    public static User generateDefaultUser() {
        return User.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public static User generateUser(String email, String password, String firstName, String lastName) {
        return User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
