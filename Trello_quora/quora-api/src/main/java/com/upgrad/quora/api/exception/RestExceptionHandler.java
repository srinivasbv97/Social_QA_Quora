package com.upgrad.quora.api.exception;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(
            UserNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse().code(exception.getCode()).message(exception.getErrorMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(
            AuthorizationFailedException exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse().code(exception.getCode()).message(exception.getErrorMessage()),
                HttpStatus.FORBIDDEN);
    }
}
