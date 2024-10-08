package com.klymenko.user.system.task.service.dao.entity;

import com.klymenko.user.system.task.service.domain.entity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    private UUID id;
    @Column
    private String email;
    @Column
    private String passwordHash;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserEntity that = (UserEntity) object;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
