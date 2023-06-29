package com.vlad.libraryjparest.exception_handling.client_exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ClientIncorrectData> handleException(
            NoSuchClientException exception){
        ClientIncorrectData data = new ClientIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ClientIncorrectData> handleException(
            ClientAlreadyExistsException exception){
        ClientIncorrectData data = new ClientIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
}
