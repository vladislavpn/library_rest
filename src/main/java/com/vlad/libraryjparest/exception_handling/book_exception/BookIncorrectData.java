package com.vlad.libraryjparest.exception_handling.book_exception;

public class BookIncorrectData {
    private String info;

    public BookIncorrectData(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
