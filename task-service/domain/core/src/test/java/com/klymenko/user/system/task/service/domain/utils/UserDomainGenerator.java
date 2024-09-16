package com.klymenko.user.system.task.service.domain.utils;

import com.klymenko.user.system.task.service.domain.entity.User;


public class UserDomainGenerator {

    private static final String DEFAULT_EMAIL = "test@mail.com";
    private static final String DEFAULT_PASSWORD_HASH = "Test password hash";
    private static final String DEFAULT_FIRST_NAME = "Test first name";
    private static final String DEFAULT_LAST_NAME = "Test last name";

    public static User generateDefaultUser() {
        return User.builder()
                .email(DEFAULT_EMAIL)
                .passwordHash(DEFAULT_PASSWORD_HASH)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public static User generateUser(String email, String passwordHash, String firstName, String lastName) {
        return User.builder()
                .email(email)
                .passwordHash(passwordHash)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
