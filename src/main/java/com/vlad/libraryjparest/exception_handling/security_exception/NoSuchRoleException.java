package com.vlad.libraryjparest.exception_handling.security_exception;

public class NoSuchRoleException extends RuntimeException{
    public NoSuchRoleException(String message) {
        super(message);
    }
}
