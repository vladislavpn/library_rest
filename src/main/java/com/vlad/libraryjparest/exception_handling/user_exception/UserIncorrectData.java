package com.vlad.libraryjparest.exception_handling.user_exception;

public class UserIncorrectData {
    private String info;

    public UserIncorrectData(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
