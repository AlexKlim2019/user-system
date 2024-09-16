package com.klymenko.user.system.task.service.auth.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;

@Getter
@Builder
public class JwtProperties {
    private Algorithm algorithm;
    private Duration ttl;
}
