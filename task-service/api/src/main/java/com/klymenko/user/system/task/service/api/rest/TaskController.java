package com.klymenko.user.system.task.service.api.rest;

import com.klymenko.user.system.task.service.domain.dto.command.task.CreateTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.DeleteTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.command.task.PatchTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.query.FindTaskQuery;
import com.klymenko.user.system.task.service.domain.dto.query.SearchTasksQuery;
import com.klymenko.user.system.task.service.domain.dto.response.task.*;
import com.klymenko.user.system.task.service.domain.port.input.service.TaskApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tasks", produces = "application/vnd.api.v1+json")
public class TaskController {
    private final TaskApplicationService taskApplicationService;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> registerUser(@Valid @RequestBody CreateTaskCommand command) {
        log.info("Task with title {} is being created...", command.title());
        var response = taskApplicationService.saveTask(command);
        log.info("Task with id {} has been created", response.task().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<FindTaskResponse> findTask(@PathVariable UUID taskId) {
        log.info("Task with id {} is being found...", taskId);
        var query = new FindTaskQuery(taskId);
        var response = taskApplicationService.findTask(query);
        log.info("Task with id {} has been found", taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<SearchTasksResponse> searchTasks(@Valid @RequestBody SearchTasksQuery query) {
        log.info("Tasks for user with id {} are being found...", query.userId());
        var response = taskApplicationService.searchTasks(query);
        log.info("{} tasks have been found", response.tasks().size());
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<PatchTaskResponse> patchTask(@Valid @RequestBody PatchTaskCommand command) {
        log.info("Task with id {} is being updated...", command.id());
        var response = taskApplicationService.partialUpdateTask(command);
        log.info("Task with id {} has been updated", command.id());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<DeleteTaskResponse> deleteTask(@Valid @RequestBody DeleteTaskCommand command) {
        log.info("Task with id {} is being deleted...", command.id());
        var response = taskApplicationService.deleteTask(command);
        log.info("Task with id {} has been deleted", command.id());
        return ResponseEntity.ok(response);
    }
}
