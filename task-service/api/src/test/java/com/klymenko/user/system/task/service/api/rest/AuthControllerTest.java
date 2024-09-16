package com.klymenko.user.system.task.service.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klymenko.user.system.task.service.api.handler.GlobalExceptionHandler;
import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import com.klymenko.user.system.task.service.domain.port.input.service.AuthApplicationService;
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
import static com.klymenko.user.system.task.service.api.utils.UserGenerator.CommandGenerator.generateValidSignUpUserCommand;
import static com.klymenko.user.system.task.service.api.utils.UserGenerator.Responses.generateSuccessSignUpUserResponse;
import static com.klymenko.user.system.task.service.domain.utils.StringUtils.concatenate;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = {AuthController.class, GlobalExceptionHandler.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthApplicationService service;

    @Nested
    class RegisterUserTests {

        @Test
        void successfulScenario() throws Exception {
            var command = generateValidSignUpUserCommand();
            var response = generateSuccessSignUpUserResponse();
            var bodyMap = generateSignUpUserValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveUser(command)).willReturn(response);

            mvc.perform(post("/api/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.user.id").value(response.user().id().toString()))
                    .andExpect(jsonPath("$.user.email").value(response.user().email()))
                    .andExpect(jsonPath("$.user.firstName").value(response.user().firstName()))
                    .andExpect(jsonPath("$.user.lastName").value(response.user().lastName()))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        void givenRequestPayloadWithDuplicatedEmail_thenReturnBadRequestResponse() throws Exception {
            var command = generateValidSignUpUserCommand();
            var bodyMap = generateSignUpUserValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            var exceptionMessage = concatenate("User with email ", command.email(), " already exists");
            given(service.saveUser(command)).willThrow(new UserDomainException(exceptionMessage));

            mvc.perform(post("/api/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message").value(exceptionMessage));
        }


        @Test
        void givenRequestPayloadWithoutEmail_thenReturnBadRequestResponse() throws Exception {
            var bodyMap = generateCreateUserBodyMapWithoutEmail();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);

            mvc.perform(post("/api/auth/signup")
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

            mvc.perform(post("/api/auth/signup")
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

            mvc.perform(post("/api/auth/signup")
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

            mvc.perform(post("/api/auth/signup")
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

            mvc.perform(post("/api/auth/signup")
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

            mvc.perform(post("/api/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message")
                            .value("The lastName field has the error: Last name is mandatory!"));
        }

        @Test
        void givenThrownUserDomainException_thenReturnBadRequestResponse() throws Exception {
            var command = generateValidSignUpUserCommand();
            var bodyMap = generateSignUpUserValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveUser(command)).willThrow(new UserDomainException("Could not save user!"));

            mvc.perform(post("/api/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(BAD_REQUEST_CODE))
                    .andExpect(jsonPath("$.message").value("Could not save user!"));
        }

        @Test
        void givenThrownGeneralException_thenReturnInternalServerErrorResponse() throws Exception {
            var command = generateValidSignUpUserCommand();
            var bodyMap = generateSignUpUserValidBodyMap();
            var jsonBody = objectMapper.writeValueAsString(bodyMap);
            given(service.saveUser(command)).willThrow(new ArrayIndexOutOfBoundsException());

            mvc.perform(post("/api/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(jsonBody))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.code").value("Internal Server Error"))
                    .andExpect(jsonPath("$.message").value(INTERNAL_SERVER_ERROR_MESSAGE));
        }
    }
}
