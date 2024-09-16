package com.klymenko.user.system.task.service.domain.port.output.security;

public interface UserPasswordEncoder {
    String encode(String password);
}
