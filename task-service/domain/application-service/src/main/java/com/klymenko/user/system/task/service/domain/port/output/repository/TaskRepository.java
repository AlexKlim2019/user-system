package com.klymenko.user.system.task.service.domain.port.output.repository;


import com.klymenko.user.system.task.service.domain.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    Task save(Task user, LocalDateTime createdAt);

    void update(Task user, LocalDateTime createdAt, LocalDateTime updatedAt);

    Optional<Task> findById(UUID id);

    List<Task> findAllByUserId(UUID userId);

    void deleteById(UUID id);
}
