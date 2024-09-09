package com.klymenko.user.system.task.service.domain.utils;


import com.klymenko.user.system.task.service.domain.entity.Task;

public class TaskDomainGenerator {

    private static final String DEFAULT_TITLE = "Test title";
    private static final String DEFAULT_DESCRIPTION = "Test description";

    public static Task generateDefaultTask() {
        return Task.builder()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .build();
    }

    public static Task generateTask(String title, String description) {
        return Task.builder()
                .title(title)
                .description(description)
                .build();
    }
}
