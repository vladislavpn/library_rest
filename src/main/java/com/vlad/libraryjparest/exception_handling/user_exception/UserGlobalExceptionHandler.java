package com.vlad.libraryjparest.exception_handling.user_exception;

import com.vlad.libraryjparest.exception_handling.client_exception.ClientAlreadyExistsException;
import com.vlad.libraryjparest.exception_handling.client_exception.ClientIncorrectData;
import com.vlad.libraryjparest.exception_handling.client_exception.NoSuchClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(
            UserAlreadyExistsException exception){
        UserIncorrectData data = new UserIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
}
