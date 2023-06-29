package com.vlad.libraryjparest.exception_handling.client_exception;

public class NoSuchClientException extends RuntimeException{
    public NoSuchClientException(String message) {
        super(message);
    }
}
