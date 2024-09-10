package com.klymenko.user.system.task.service.domain.dto.command.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.klymenko.user.system.task.service.domain.constants.DomainConstants.EMAIL_REGEX;

@Builder
public record CreateUserCommand(
        @NotBlank(message = "Email is mandatory!")
        @Email(regexp = EMAIL_REGEX, message = "Email is not correct!")
        String email,
        @NotBlank(message = "Password is mandatory!")
        @Size(min = 6, message = "The password should be at least 6 characters")
        String password,
        @NotBlank(message = "First name is mandatory!")
        String firstName,
        @NotBlank(message = "Last name is mandatory!")
        String lastName
) {}
