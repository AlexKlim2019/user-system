package com.klymenko.user.system.task.service.domain.dto.command.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static com.klymenko.user.system.task.service.domain.constants.DomainConstants.EMAIL_REGEX;

@Builder
public record SignInCommand(
        @NotBlank(message = "Email is mandatory!")
        @Email(regexp = EMAIL_REGEX, message = "Email is not correct!")
        String email,
        @NotBlank(message = "Password is mandatory!")
        String password
) {}
