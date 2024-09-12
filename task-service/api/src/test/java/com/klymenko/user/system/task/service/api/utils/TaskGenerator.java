package com.klymenko.user.system.task.service.api.utils;


import com.klymenko.user.system.task.service.domain.dto.command.task.CreateTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.PatchTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.response.task.CreateTaskResponse;
import com.klymenko.user.system.task.service.domain.dto.response.task.FindTaskResponse;
import com.klymenko.user.system.task.service.domain.dto.response.task.SearchTasksResponse;
import com.klymenko.user.system.task.service.domain.entity.Task;

import java.util.Collections;
import java.util.List;

import static com.klymenko.user.system.task.service.api.utils.TestConstants.*;

public class TaskGenerator {
    public static Task generateValidTask() {
        return Task.builder()
                .id(TASK_ID)
                .title(VALID_TITLE)
                .description(VALID_DESCRIPTION)
                .userId(USER_ID)
                .build();
    }

    public static class CommandGenerator {
        public static CreateTaskCommand generateValidCreateTaskCommand() {
            return CreateTaskCommand.builder()
                    .title(VALID_TITLE)
                    .description(VALID_DESCRIPTION)
                    .userId(USER_ID)
                    .build();
        }

        public static PatchTaskCommand generateValidPatchTaskCommand() {
            return PatchTaskCommand.builder()
                    .id(TASK_ID)
                    .title(UPDATED_TITLE)
                    .description(UPDATED_DESCRIPTION)
                    .build();
        }
    }

    public static class Responses {
        public static CreateTaskResponse generateSuccessCreateTaskResponse() {
            return CreateTaskResponse.builder()
                    .task(generateValidTask())
                    .message("Create task response message")
                    .build();
        }

        public static FindTaskResponse generateSuccessFindTaskResponse() {
            return FindTaskResponse.builder()
                    .task(generateValidTask())
                    .message("Find task response message")
                    .build();
        }

        public static SearchTasksResponse generateSuccessSearchTasksResponse() {
            return SearchTasksResponse.builder()
                    .tasks(List.of(generateValidTask()))
                    .message("Search tasks response message")
                    .build();
        }

        public static SearchTasksResponse generateSearchTasksResponseWithEmptyList() {
            return SearchTasksResponse.builder()
                    .tasks(Collections.emptyList())
                    .message("There are no tasks for the given user")
                    .build();
        }
    }
}
