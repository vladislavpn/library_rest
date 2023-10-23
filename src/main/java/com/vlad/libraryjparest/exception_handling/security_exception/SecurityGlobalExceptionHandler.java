package com.vlad.libraryjparest.exception_handling.security_exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AuthIncorrectData> handleException(
            UserAlreadyExistsException exception){
        AuthIncorrectData data = new AuthIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AuthIncorrectData> handleException(
            NoSuchRoleException exception){
        AuthIncorrectData data = new AuthIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
