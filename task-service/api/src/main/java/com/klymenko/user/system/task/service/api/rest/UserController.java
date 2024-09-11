package com.klymenko.user.system.task.service.api.rest;

import com.klymenko.user.system.task.service.domain.dto.command.user.CreateUserCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.CreateUserResponse;
import com.klymenko.user.system.task.service.domain.port.input.service.UserApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UserController {
    private final UserApplicationService userApplicationService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> registerUser(@Valid @RequestBody CreateUserCommand command) {
        log.info("User with email {} is being created...", command.email());
        var response = userApplicationService.saveUser(command);
        log.info("User with id {} has been created", response.user().getId());
        return ResponseEntity.ok(response);
    }
}
