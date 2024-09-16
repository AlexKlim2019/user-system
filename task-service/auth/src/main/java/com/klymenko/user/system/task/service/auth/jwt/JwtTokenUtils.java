package com.klymenko.user.system.task.service.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.*;
import com.klymenko.user.system.task.service.auth.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    private final JwtProperties jwtProperties;

    public String generateJwtToken(Authentication authentication) {
        var userPrincipal = (JwtUser) authentication.getPrincipal();
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(ZonedDateTime.now().plus(jwtProperties.getTtl()).toInstant())
                .sign(jwtProperties.getAlgorithm());
    }

    public String findUsernameFromToken(String token) {
        return JWT.require(jwtProperties.getAlgorithm()).build().verify(token).getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            JWT.require(jwtProperties.getAlgorithm()).build().verify(token);
            return true;
        } catch (AlgorithmMismatchException exception) {
            log.error("Invalid algorithm: {}", exception.getMessage());
        } catch (SignatureVerificationException exception) {
            log.error("Signature of token verification fails: {}", exception.getMessage());
        } catch (TokenExpiredException exception) {
            log.error("JWT token is expired: {}", exception.getMessage());
        } catch (MissingClaimException exception) {
            log.error("JWT claims string is empty: {}", exception.getMessage());
        } catch (IncorrectClaimException exception) {
            log.error("JWT claim contained a different value than the expected one: {}", exception.getMessage());
        }
        return false;
    }
}
