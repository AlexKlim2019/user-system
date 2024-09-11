package com.klymenko.user.system.task.service.dao.repository;

import com.klymenko.user.system.task.service.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findAllByUserId(UUID userId);
}
