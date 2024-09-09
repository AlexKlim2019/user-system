package com.klymenko.user.system.task.service.domain.entity;

import com.klymenko.user.system.task.service.domain.exception.TaskDomainException;

import java.util.UUID;

public class Task extends BaseEntity {
    private String title;
    private String description;
    private UUID userId;

    public Task(Builder builder) {
        super.setId(builder.id);
        this.title = builder.title;
        this.description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeTask() {
        setId(UUID.randomUUID());
    }

    public void validateTask() {
       validateTitle();
       validateDescription();
    }

    private void validateTitle() {
        if (this.title.isBlank()) {
            throw new TaskDomainException("The title should not be empty");
        }
    }

    public void validateDescription() {
        if (this.description.isBlank()) {
            throw new TaskDomainException("The description should not be empty");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public static final class Builder {
        private UUID id;
        private String title;
        private String description;

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
