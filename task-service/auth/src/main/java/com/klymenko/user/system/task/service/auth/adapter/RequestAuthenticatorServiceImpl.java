package com.klymenko.user.system.task.service.auth.adapter;

import com.klymenko.user.system.task.service.auth.jwt.JwtTokenUtils;
import com.klymenko.user.system.task.service.auth.jwt.JwtUser;
import com.klymenko.user.system.task.service.auth.service.UserDataMapperImpl;
import com.klymenko.user.system.task.service.domain.dto.response.user.AuthResponse;
import com.klymenko.user.system.task.service.domain.port.output.security.RequestAuthenticatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestAuthenticatorServiceImpl implements RequestAuthenticatorService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDataMapperImpl userDataMapper;

    @Override
    public AuthResponse authenticate(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtTokenUtils.generateJwtToken(authentication);
        var userView = userDataMapper.jwtUserToUserView((JwtUser) authentication.getPrincipal());
        return new AuthResponse(userView, token, "User with has been created successfully");
    }
}
