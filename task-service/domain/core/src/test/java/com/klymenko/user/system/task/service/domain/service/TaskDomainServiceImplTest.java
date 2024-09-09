package com.klymenko.user.system.task.service.domain.service;

import com.klymenko.user.system.task.service.domain.exception.TaskDomainException;
import com.klymenko.user.system.task.service.domain.service.impl.TaskDomainServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.klymenko.user.system.task.service.domain.utils.TaskDomainGenerator.generateDefaultTask;
import static com.klymenko.user.system.task.service.domain.utils.TaskDomainGenerator.generateTask;
import static org.assertj.core.api.Assertions.*;

class TaskDomainServiceImplTest {
    private final TaskDomainService service = new TaskDomainServiceImpl();

    @Test
    void successfulScenario() {
        assertThatCode(() -> {
            var result = service.validateAndInitiateTask(generateDefaultTask());
            assertThat(result.getTask().getId()).isNotNull();
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "\n", " "})
    void givenInvalidTitle_whenValidateAndInitiateTask_thenThrowException(String value) {
        assertThatThrownBy(() -> {
            var task = generateTask(value, "Test description");
            service.validateAndInitiateTask(task);
        }).isInstanceOf(TaskDomainException.class).hasMessage("The title should not be empty");
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "\n", " "})
    void givenInvalidDescription_whenValidateAndInitiateTask_thenThrowException(String value) {
        assertThatThrownBy(() -> {
            var task = generateTask("Test title", value);
            service.validateAndInitiateTask(task);
        }).isInstanceOf(TaskDomainException.class).hasMessage("The description should not be empty");
    }
}