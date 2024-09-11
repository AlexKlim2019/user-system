package com.klymenko.user.system.task.service.api.handler;

import com.klymenko.user.system.task.service.api.handler.response.ErrorResponse;
import com.klymenko.user.system.task.service.domain.exception.UserDomainException;
import com.klymenko.user.system.task.service.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Unexpected error";

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(INTERNAL_SERVER_ERROR_MESSAGE)
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var fieldErrors = exception.getBindingResult().getFieldErrors();
        var errorMessage = fieldErrors.isEmpty() ?
                exception.getBindingResult().getAllErrors().getFirst().getDefaultMessage() :
                extractViolationsFromException(fieldErrors);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorMessage)
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {UserDomainException.class})
    public ErrorResponse handleUserDomainException(UserDomainException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    private String extractViolationsFromException(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(error -> String.format("The %s field has the error: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("--"));
    }
}
