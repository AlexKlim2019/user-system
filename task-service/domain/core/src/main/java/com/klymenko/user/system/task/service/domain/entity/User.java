package com.klymenko.user.system.task.service.domain.entity;

import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class User extends BaseEntity {
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private UserRole role;

    public User(Builder builder) {
        super.setId(builder.id);
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeUser() {
        setId(UUID.randomUUID());
    }

    public void validateUser() {
        validateEmail();
        validatePassword();
        validateFirstName();
        validateLastName();
    }

    private void validateEmail() {
        if (this.email.isBlank()) {
            throw new UserDomainException("The email should not be empty");
        }
        if (!this.email.contains("@")) {
            throw new UserDomainException("The email should be a valid email address");
        }
    }

    private void validatePassword() {
        if (this.passwordHash.isBlank()) {
            throw new UserDomainException("The password should not be empty");
        }

        if (this.passwordHash.length() < 6) {
            throw new UserDomainException("The password should be at least 6 characters");
        }
    }

    private void validateFirstName() {
        if (this.firstName.isBlank()) {
            throw new UserDomainException("The first name should not be empty");
        }
    }

    private void validateLastName() {
        if (this.lastName.isBlank()) {
            throw new UserDomainException("The last name should not be empty");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public static final class Builder {
        private UUID id;
        private String email;
        public UserRole role;
        private String firstName;
        private String lastName;
        private String passwordHash;

        private Builder() {}

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
