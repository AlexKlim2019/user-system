package com.klymenko.user.system.task.service.auth.service;

import com.klymenko.user.system.task.service.auth.jwt.JwtUser;
import com.klymenko.user.system.task.service.domain.exception.UserNotFoundException;
import com.klymenko.user.system.task.service.domain.port.output.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.klymenko.user.system.task.service.domain.utils.StringUtils.concatenate;
import static com.klymenko.user.system.task.service.domain.utils.StringUtils.normalizeEmail;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var normalizedEmail = normalizeEmail(username);
        var user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        concatenate("User with email ", username, " not found"))
                );
        return JwtUser.build(user);
    }
}
