package com.klymenko.user.system.task.service.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.klymenko.user.system.task.service.domain.utils.StringUtils.concatenate;


@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_HEADER_NAME = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseTokenFromHeader(request);
            if (token != null && jwtTokenUtils.validateJwtToken(token)) {
                String username = jwtTokenUtils.findUsernameFromToken(token);

                var securityContext = SecurityContextHolder.getContext();
                if (securityContext.getAuthentication() == null) {
                    log.info("Security context is empty, so user authentication is running");
                    authenticateUser(securityContext, username, request);
                }
            }
        } catch (Exception exception) {
            log.error("Cannot set user authentication: {}", exception.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String parseTokenFromHeader(HttpServletRequest request) {
        var authHeader = request.getHeader(JWT_TOKEN_HEADER_NAME);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(JWT_TOKEN_PREFIX)) {
            return authHeader.substring(JWT_TOKEN_PREFIX.length());
        }
        return null;
    }

    private void authenticateUser(SecurityContext securityContext, String username, HttpServletRequest request) {
        try {
            var userDetails = userDetailsService.loadUserByUsername(username);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.debug(concatenate("Authorized user ", username, " setting security context"));
            securityContext.setAuthentication(authentication);
        } catch (UsernameNotFoundException exception) {
            log.error("Cannot authenticate user. Username not found: {}", exception.getMessage());
        }
    }
}