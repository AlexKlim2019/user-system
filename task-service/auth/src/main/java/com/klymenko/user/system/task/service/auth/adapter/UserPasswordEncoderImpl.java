package com.klymenko.user.system.task.service.auth.adapter;

import com.klymenko.user.system.task.service.domain.port.output.security.UserPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPasswordEncoderImpl implements UserPasswordEncoder {

    private final PasswordEncoder encoder;

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}
