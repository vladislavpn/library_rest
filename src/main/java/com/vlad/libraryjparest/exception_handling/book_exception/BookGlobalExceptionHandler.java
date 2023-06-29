package com.vlad.libraryjparest.exception_handling.book_exception;

import com.vlad.libraryjparest.exception_handling.client_exception.ClientIncorrectData;
import com.vlad.libraryjparest.exception_handling.client_exception.NoSuchClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BookIncorrectData> handleException(
            NoSuchBookException exception){
        BookIncorrectData data = new BookIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookIncorrectData> handleException(
            BookAlreadyExistsException exception){
        BookIncorrectData data = new BookIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }
}
