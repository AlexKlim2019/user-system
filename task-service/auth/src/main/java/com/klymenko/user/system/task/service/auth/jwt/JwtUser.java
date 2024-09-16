package com.klymenko.user.system.task.service.auth.jwt;

import com.klymenko.user.system.task.service.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class JwtUser implements UserDetails {
    @Getter
    private UUID id;
    private String username;
    private String password;
    @Getter
    private String fistName;
    @Getter
    private String lastName;
    private Collection<? extends GrantedAuthority> authorities;

    public static JwtUser build(User user) {
        List<GrantedAuthority> authorities = Stream.of(user.getRole())
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(toList());

        return new JwtUser(user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getFirstName(),
                user.getLastName(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        JwtUser user = (JwtUser) other;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        var result = id.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}
