package com.klymenko.user.system.task.service.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klymenko.user.system.task.service.api.handler.GlobalExceptionHandler;
import com.klymenko.user.system.task.service.domain.port.input.service.UserApplicationService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.klymenko.user.system.task.service.api.handler.GlobalExceptionHandler.INTERNAL_SERVER_ERROR_MESSAGE;
import static com.klymenko.user.system.task.service.api.utils.BodyMapGenerator.CreateUserPayloadGenerator.*;
import static com.klymenko.user.system.task.service.api.utils.TestConstants.BAD_REQUEST_CODE;
import static com.klymenko.user.system.task.service.api.utils.UserGenerator.CommandGenerator.generateValidCreateUserCommand;
import static com.klymenko.user.system.task.service.api.utils.UserGenerator.Responses.generateSuccessCreateUserResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {UserController.class, GlobalExceptionHandler.class})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserApplicationService service;

    @Nested
    class CreateUserTests {

        @Test
        void successfulScenario() throws Exception {
            var command = generateValidCreateUserCommand();
            var response = generateSuccessCreateUserResponse();
            var bodyMap = generateCreateUserValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveUser(command)).willReturn(response);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.user.id").value(response.user().getId().toString()))
                    .andExpect(jsonPath("$.user.email").value(response.user().getEmail()))
                    .andExpect(jsonPath("$.user.password").value(response.user().getPassword()))
                    .andExpect(jsonPath("$.user.firstName").value(response.user().getFirstName()))
                    .andExpect(jsonPath("$.user.lastName").value(response.user().getLastName()))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void givenRequestPayloadWithoutEmail_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateUserBodyMapWithoutEmail();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The email field has the error: Email is mandatory!"));
        }

        @Test
        void givenRequestPayloadWithInvalidEmail_thenReturnBadRequestResponse() throws Exception {
            var invalidEmail = "test.mail.com";
            var bodyMap = generateCreateUserBodyMapWithInvalidEmail(invalidEmail);
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The email field has the error: Email is not correct!"));
        }

        @Test
        void givenRequestPayloadWithoutPassword_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateUserBodyMapWithoutPassword();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The password field has the error: Password is mandatory!"));
        }

        @Test
        void givenRequestPayloadWithInvalidPassword_thenReturnBadRequestResponse() throws Exception {
            var invalidPassword = "pass";
            var bodyMap = generateCreateUserBodyMapWithInvalidPassword(invalidPassword);
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The password field has the error: The password should be at least 6 characters!"));
        }

        @Test
        void givenRequestPayloadWithoutFirstName_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateUserBodyMapWithoutFirstName();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The firstName field has the error: First name is mandatory!"));
        }

        @Test
        void givenRequestPayloadWithoutLastName_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateUserBodyMapWithoutLastName();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The lastName field has the error: Last name is mandatory!"));
        }

        @Test
        void givenThrownGeneralException_thenReturnInternalServerErrorResponse() throws Exception {
            var command = generateValidCreateUserCommand();
            var bodyMap = generateCreateUserValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveUser(command)).willThrow(new ArrayIndexOutOfBoundsException());

            mvc.perform(post("/users")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.code").value("Internal Server Error"))
                    .andExpect(jsonPath("$.message").value(INTERNAL_SERVER_ERROR_MESSAGE));
        }
    }
}
