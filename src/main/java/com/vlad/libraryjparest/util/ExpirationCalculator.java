package com.vlad.libraryjparest.util;

import com.vlad.libraryjparest.entity.Book;


public interface ExpirationCalculator {
    public void calculate(Book book);
}
