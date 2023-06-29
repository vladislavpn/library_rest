package com.vlad.libraryjparest.exception_handling.book_exception;

public class NoSuchBookException extends RuntimeException{
    public NoSuchBookException(String message) {
        super(message);
    }
}
