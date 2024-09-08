package com.klymenko.user.system.task.service.domain.entity;

import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class User extends BaseEntity {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User(Builder builder) {
        super.setId(builder.id);
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
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
        if (this.password.isBlank()) {
            throw new UserDomainException("The password should not be empty");
        }

        if (this.password.length() < 6) {
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public static final class Builder {
        private UUID id;
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
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

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
