package com.vlad.libraryjparest.util;

import com.vlad.libraryjparest.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ExpirationByDays implements ExpirationCalculator{
    private int daysToExpire = 10;

    @Override
    public void calculate(Book book) {
        if(book == null) return;
        if(book.getDateAcquired() == null) return;
        Date dateAcquired = book.getDateAcquired();
        long diffMillies = new Date().getTime() - dateAcquired.getTime();
        long diffDays = TimeUnit.DAYS.convert(diffMillies, TimeUnit.MILLISECONDS);
        if(diffDays > daysToExpire) book.setExpired(true);
        else book.setExpired(false);
    }
}
