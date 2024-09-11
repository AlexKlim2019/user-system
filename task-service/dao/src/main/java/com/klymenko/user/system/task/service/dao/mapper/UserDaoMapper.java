package com.klymenko.user.system.task.service.dao.mapper;

import com.klymenko.user.system.task.service.dao.entity.UserEntity;
import com.klymenko.user.system.task.service.domain.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserDaoMapper {

    public UserEntity userToUserEntity(User user, LocalDateTime createdAt) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(createdAt)
                .build();
    }

    public User userEntityToUser(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
}
