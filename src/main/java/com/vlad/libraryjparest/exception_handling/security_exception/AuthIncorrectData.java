package com.vlad.libraryjparest.exception_handling.security_exception;

public class AuthIncorrectData {
    private String info;

    public AuthIncorrectData(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
