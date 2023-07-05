package com.vlad.libraryjparest.repository;

import com.vlad.libraryjparest.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book("Test", "Entity", 1);
        book2 = new Book("Test", "Entity", 2);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @AfterEach
    void tearDown(){
        bookRepository.deleteAll();
    }

    @Test
    void canFindByTitle() {
        String title = "Test";
        List<Book> books = bookRepository.findAllByTitle(title, any());
        assertTrue(books.stream().map(o -> o.getTitle()).
                allMatch(o -> o.equals(title)));
        assertTrue(books.size()==2);
    }

    @Test
    void canFindByAuthor() {
        String author = "Entity";
        List<Book> books = bookRepository.findByAuthor(author, any());
        assertTrue(books.stream().map(o -> o.getAuthor()).
                allMatch(o -> o.equals(author)));
        assertTrue(books.size()==2);
    }

    @Test
    void canFindByAuthorAndTitle() {
        String testTitle = "Test";
        String testAuthor = "Entity";
        List<Book> books = bookRepository.findAllByAuthorAndTitle(testAuthor, testTitle, any());
        assertTrue(books.size()==2);
        assertTrue(books.stream().map(o -> o.getTitle()).allMatch(o -> o.equals(testTitle)));
        assertTrue(books.stream().map(o -> o.getAuthor()).allMatch(o -> o.equals(testAuthor)));
    }

    @Test
    void canReturnExistsBookByTitleAndAuthorAndPublished() {
        assertTrue(bookRepository
                .existsBookByTitleAndAuthorAndPublished(book1.getTitle(),
                        book1.getAuthor(), book1.getPublished()));
        assertFalse(bookRepository
                .existsBookByTitleAndAuthorAndPublished("Not exists",
                        book1.getAuthor(), book1.getPublished()));
        assertFalse(bookRepository
                .existsBookByTitleAndAuthorAndPublished(book1.getTitle(),
                        "Not exists", book1.getPublished()));
        assertFalse(bookRepository.existsBookByTitleAndAuthorAndPublished(book1.getTitle(),
                book1.getAuthor(), 0));
    }

}