package com.klymenko.user.system.task.service.domain.port.output.security;

import com.klymenko.user.system.task.service.domain.dto.response.user.AuthResponse;

public interface RequestAuthenticatorService {
    AuthResponse authenticate(String email, String password);
}
