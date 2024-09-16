package com.klymenko.user.system.task.service.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klymenko.user.system.task.service.api.handler.GlobalExceptionHandler;
import com.klymenko.user.system.task.service.domain.dto.command.task.DeleteTaskCommand;
import com.klymenko.user.system.task.service.domain.dto.query.FindTaskQuery;
import com.klymenko.user.system.task.service.domain.dto.query.SearchTasksQuery;
import com.klymenko.user.system.task.service.domain.dto.response.task.DeleteTaskResponse;
import com.klymenko.user.system.task.service.domain.dto.response.task.PatchTaskResponse;
import com.klymenko.user.system.task.service.domain.exception.TaskDomainException;
import com.klymenko.user.system.task.service.domain.exception.TaskNotFoundException;
import com.klymenko.user.system.task.service.domain.port.input.service.TaskApplicationService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.klymenko.user.system.task.service.api.handler.GlobalExceptionHandler.INTERNAL_SERVER_ERROR_MESSAGE;
import static com.klymenko.user.system.task.service.api.utils.BodyMapGenerator.CreateTaskPayloadGenerator.*;
import static com.klymenko.user.system.task.service.api.utils.TaskGenerator.CommandGenerator.generateValidCreateTaskCommand;
import static com.klymenko.user.system.task.service.api.utils.TaskGenerator.CommandGenerator.generateValidPatchTaskCommand;
import static com.klymenko.user.system.task.service.api.utils.TaskGenerator.Responses.*;
import static com.klymenko.user.system.task.service.api.utils.TestConstants.*;
import static com.klymenko.user.system.task.service.domain.utils.StringUtils.concatenate;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = {TaskController.class, GlobalExceptionHandler.class})
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TaskApplicationService service;

    @Nested
    class CreateTaskTests {

        @Test
        void successfulScenario() throws Exception {
            var command = generateValidCreateTaskCommand();
            var response = generateSuccessCreateTaskResponse();
            var bodyMap = generateCreateTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveTask(command)).willReturn(response);

            mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.task.id").value(response.task().getId().toString()))
                    .andExpect(jsonPath("$.task.title").value(response.task().getTitle()))
                    .andExpect(jsonPath("$.task.description").value(response.task().getDescription()))
                    .andExpect(jsonPath("$.task.userId").value(response.task().getUserId().toString()))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void givenRequestPayloadWithoutTitle_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateTaskBodyMapWithoutTitle();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The title field has the error: Title is mandatory!"));
        }

        @Test
        void givenRequestPayloadWithoutDescription_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateTaskBodyMapWithoutDescription();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The description field has the error: Description is mandatory!"));
        }

        @Test
        void givenRequestPayloadWithoutUserId_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateTaskBodyMapWithoutUserId();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The userId field has the error: User id is mandatory!"));
        }

        @Test
        void givenThrownTaskDomainException_thenReturnBadRequestResponse() throws Exception {
            var command = generateValidCreateTaskCommand();
            var bodyMap = generateCreateTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveTask(command)).willThrow(new TaskDomainException("Could not save task!"));

            mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message").value("Could not save task!"));
        }

        @Test
        void givenThrownGeneralException_thenReturnInternalServerErrorResponse() throws Exception {
            var command = generateValidCreateTaskCommand();
            var bodyMap = generateCreateTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveTask(command)).willThrow(new IllegalArgumentException());

            mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.code").value("Internal Server Error"))
                    .andExpect(jsonPath("$.message").value(INTERNAL_SERVER_ERROR_MESSAGE));
        }
    }

    @Nested
    class FindTaskTests {

        @Test
        void successfulScenario() throws Exception {
            var response = generateSuccessFindTaskResponse();
            given(service.findTask(new FindTaskQuery(TASK_ID))).willReturn(response);

            var url = concatenate("/api/tasks/", TASK_ID.toString());
            mvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.task.id").value(response.task().getId().toString()))
                    .andExpect(jsonPath("$.task.title").value(response.task().getTitle()))
                    .andExpect(jsonPath("$.task.description").value(response.task().getDescription()))
                    .andExpect(jsonPath("$.task.userId").value(response.task().getUserId().toString()))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void givenRequestForUnknownTask_thenReturnNotFound() throws Exception {
            given(service.findTask(new FindTaskQuery(TASK_ID)))
                    .willThrow(new TaskNotFoundException("Task not found with given id!"));

            var url = String.format("/api/tasks/%s", TASK_ID);
            mvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(NOT_FOUND_CODE))
                    .andExpect(jsonPath("$.message").value("Task not found with given id!"));
        }
    }

    @Nested
    class SearchTasksTests {

        @Test
        void successfulScenario() throws Exception {
            var response = generateSuccessSearchTasksResponse();
            var foundTask = response.tasks().getFirst();
            var body = generateSearchTasksValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(body);
            given(service.searchTasks(new SearchTasksQuery(USER_ID))).willReturn(response);

            mvc.perform(get("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.tasks").isArray())
                    .andExpect(jsonPath("$.tasks").isNotEmpty())
                    .andExpect(jsonPath("$.tasks[0].id").value(foundTask.getId().toString()))
                    .andExpect(jsonPath("$.tasks[0].title").value(foundTask.getTitle()))
                    .andExpect(jsonPath("$.tasks[0].description").value(foundTask.getDescription()))
                    .andExpect(jsonPath("$.tasks[0].userId").value(foundTask.getUserId().toString()))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void successfulResultWithEmptyList() throws Exception {
            var response = generateSearchTasksResponseWithEmptyList();
            var body = generateSearchTasksValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(body);
            given(service.searchTasks(new SearchTasksQuery(USER_ID))).willReturn(response);

            mvc.perform(get("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.tasks").isArray())
                    .andExpect(jsonPath("$.tasks").isEmpty())
                    .andExpect(jsonPath("$.message").value(response.message()));
        }
    }

    @Nested
    class PatchTaskTests {

        @Test
        void successfulScenario() throws Exception {
            var command = generateValidPatchTaskCommand();
            var response = new PatchTaskResponse("Task has been updated partially and successfully");
            var bodyMap = generatePatchTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.partialUpdateTask(command)).willReturn(response);

            mvc.perform(patch("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void givenThrownTaskNotFoundException_thenReturnBadRequestResponse() throws Exception {
            var command = generateValidPatchTaskCommand();
            var bodyMap = generatePatchTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.partialUpdateTask(command))
                    .willThrow(new TaskNotFoundException("Task not found with given id!"));

            mvc.perform(patch("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(NOT_FOUND_CODE))
                    .andExpect(jsonPath("$.message").value("Task not found with given id!"));
        }
    }

    @Nested
    class DeleteTaskTests {

        @Test
        void successfulScenario() throws Exception {
            var command = new DeleteTaskCommand(TASK_ID);
            var response = new DeleteTaskResponse(concatenate("Task with id", TASK_ID.toString(), "has been deleted successfully"));
            var bodyMap = generateDeleteTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.deleteTask(command)).willReturn(response);

            mvc.perform(delete("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void givenThrownTaskNotFoundException_thenReturnBadRequestResponse() throws Exception {
            var command = new DeleteTaskCommand(TASK_ID);
            var bodyMap = generateDeleteTaskValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.deleteTask(command))
                    .willThrow(new TaskNotFoundException("Task not found with given id!"));

            mvc.perform(delete("/api/tasks")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(NOT_FOUND_CODE))
                    .andExpect(jsonPath("$.message").value("Task not found with given id!"));
        }
    }
}