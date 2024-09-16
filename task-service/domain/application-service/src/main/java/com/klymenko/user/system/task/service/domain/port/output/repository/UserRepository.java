package com.klymenko.user.system.task.service.domain.port.output.repository;


import com.klymenko.user.system.task.service.domain.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository {

    User save(User user, LocalDateTime createdAt);

    Optional<User> findByEmail(String email);
}
