package com.klymenko.user.system.task.service.domain.service.impl;

import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import com.klymenko.user.system.task.service.domain.service.UserDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.klymenko.user.system.task.service.domain.utils.UserDomainGenerator.generateDefaultUser;
import static com.klymenko.user.system.task.service.domain.utils.UserDomainGenerator.generateUser;
import static org.assertj.core.api.Assertions.*;

class UserDomainServiceImplTest {
    private final UserDomainService service = new UserDomainServiceImpl();

    @Test
    void successfulScenario() {
        assertThatCode(() -> {
            var result = service.validateAndInitiateUser(generateDefaultUser());
            assertThat(result.getUser().getId()).isNotNull();
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "\n", " ", "test.mail.com"})
    void givenInvalidEmail_whenValidateAndInitiateUser_thenThrowException(String value) {
        assertThatThrownBy(() -> {
            var user = generateUser(value, "Test password", "Test first name", "Test last name");
            service.validateAndInitiateUser(user);
        }).isInstanceOf(UserDomainException.class);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "\n", " "})
    void givenInvalidPassword_whenValidateAndInitiateUser_thenThrowException(String value) {
        assertThatThrownBy(() -> {
            var user = generateUser("test@mail.com", value, "Test first name", "Test last name");
            service.validateAndInitiateUser(user);
        }).isInstanceOf(UserDomainException.class).hasMessage("The password should not be empty");
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "\n", " "})
    void givenInvalidFirstName_whenValidateAndInitiateUser_thenThrowException(String value) {
        assertThatThrownBy(() -> {
            var user = generateUser("test@mail.com", "Test password", value, "Test last name");
            service.validateAndInitiateUser(user);
        }).isInstanceOf(UserDomainException.class).hasMessage("The first name should not be empty");
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "\n", " "})
    void givenInvalidLastName_whenValidateAndInitiateUser_thenThrowException(String value) {
        assertThatThrownBy(() -> {
            var user = generateUser("test@mail.com", "Test password", "Test first name", value);
            service.validateAndInitiateUser(user);
        }).isInstanceOf(UserDomainException.class).hasMessage("The last name should not be empty");
    }
}