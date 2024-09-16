package com.klymenko.user.system.task.service.domain.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.klymenko.user.system.task.service.auth.config.JwtProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class JwtConfig {

    @Value("${JWT_SECRET}")
    private String secret;
    @Value("${jwt.ttl}")
    private Duration ttl;

    @Bean
    @ConfigurationProperties("jwt")
    public JwtProperties jwtProperties() {
        return JwtProperties.builder()
                .algorithm(algorithm())
                .ttl(ttl)
                .build();
    }

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }
}
