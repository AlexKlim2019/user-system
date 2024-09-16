package com.klymenko.user.system.task.service.api.rest;

import com.klymenko.user.system.task.service.domain.dto.command.user.SignInCommand;
import com.klymenko.user.system.task.service.domain.dto.command.user.SignUpCommand;
import com.klymenko.user.system.task.service.domain.dto.response.user.AuthResponse;
import com.klymenko.user.system.task.service.domain.dto.response.user.RegisterUserResponse;
import com.klymenko.user.system.task.service.domain.port.input.service.AuthApplicationService;
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
@RequestMapping(value = "/api/auth", produces = "application/vnd.api.v1+json")
public class AuthController {
    private final AuthApplicationService authApplicationService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody SignUpCommand command) {
        log.info("User with email {} is being registered...", command.email());
        var response = authApplicationService.saveUser(command);
        log.info("User with id {} has been created successfully", response.user().id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody SignInCommand command) {
        log.info("User with email {} is being authenticated...", command.email());
        var response = authApplicationService.authenticateUser(command);
        log.info("User with email {} has been authenticated successfully", command.email());
        return ResponseEntity.ok(response);
    }
}
