package com.klymenko.user.system.task.service.dao.adapter;

import com.klymenko.user.system.task.service.dao.mapper.TaskDaoMapper;
import com.klymenko.user.system.task.service.dao.repository.TaskJpaRepository;
import com.klymenko.user.system.task.service.domain.entity.Task;
import com.klymenko.user.system.task.service.domain.exception.TaskNotFoundException;
import com.klymenko.user.system.task.service.domain.port.output.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository repository;
    private final TaskDaoMapper mapper;

    @Override
    public Task save(Task task, LocalDateTime createdAt) {
        var entity = mapper.taskToTaskEntity(task, createdAt, createdAt);
        var savedEntity = repository.save(entity);
        return mapper.taskEntityToTask(savedEntity);
    }

    @Override
    public void update(Task task, LocalDateTime updatedAt) {
        var existedEntity = repository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found with given id!"));
        var entity = repository.save(mapper.taskToTaskEntity(task, existedEntity.getCreatedAt(), updatedAt));
        repository.save(entity);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id).map(mapper::taskEntityToTask);
    }

    @Override
    public List<Task> findAllByUserId(UUID userId) {
        return repository.findAllByUserId(userId).stream()
                .map(mapper::taskEntityToTask)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
