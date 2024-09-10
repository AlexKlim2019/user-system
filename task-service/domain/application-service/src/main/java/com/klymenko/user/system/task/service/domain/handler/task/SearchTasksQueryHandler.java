package com.klymenko.user.system.task.service.domain.handler.task;

import com.klymenko.user.system.task.service.domain.dto.query.SearchTasksQuery;
import com.klymenko.user.system.task.service.domain.dto.response.task.SearchTasksResponse;
import com.klymenko.user.system.task.service.domain.port.output.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchTasksQueryHandler {

    private final TaskRepository repository;

    @Transactional
    public SearchTasksResponse handle(SearchTasksQuery query) {
        var tasks = repository.findAllByUserId(query.userId());
        if (tasks.isEmpty()) {
            return new SearchTasksResponse(tasks, "There are no tasks for the given user");
        }
        return new SearchTasksResponse(tasks, "Tasks has been found successfully");
    }
}
