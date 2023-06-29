package com.vlad.libraryjparest.exception_handling.client_exception;

public class ClientIncorrectData {
    private String info;

    public ClientIncorrectData(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
