package com.vlad.libraryjparest.util;

import com.vlad.libraryjparest.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExpirationByDaysTest {

    private Book book;

    @InjectMocks
    private ExpirationByDays calculator;


    @BeforeEach
    void setUp() {
        book = new Book("Test", "Test", 1);
    }

    @Test
    void shouldReturnFalse() {
        book.setDateAcquired(new Date());
        calculator.calculate(book);
        assertFalse(book.getExpired());
    }

    @Test
    void shouldReturnTrue() {
        book.setDateAcquired(new GregorianCalendar(2023, 05, 05).getTime());
        calculator.calculate(book);
        assertTrue(book.getExpired());
    }
}