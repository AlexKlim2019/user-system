package com.klymenko.user.system.task.service.dao.adapter;

import com.klymenko.user.system.task.service.dao.mapper.UserDaoMapper;
import com.klymenko.user.system.task.service.dao.repository.UserJpaRepository;
import com.klymenko.user.system.task.service.domain.entity.User;
import com.klymenko.user.system.task.service.domain.port.output.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserDaoMapper mapper;

    @Override
    public User save(User user, LocalDateTime createdAt) {
        var entity = mapper.userToUserEntity(user, createdAt);
        var savedEntity = repository.save(entity);
        return mapper.userEntityToUser(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::userEntityToUser);
    }
}
