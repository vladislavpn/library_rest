package com.vlad.libraryjparest.exception_handling;

public class NoSuchClientException extends RuntimeException{
    public NoSuchClientException(String message) {
        super(message);
    }
}
