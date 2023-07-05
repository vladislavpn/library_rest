package com.vlad.libraryjparest.service;

import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.exception_handling.book_exception.BookAlreadyExistsException;
import com.vlad.libraryjparest.exception_handling.book_exception.NoSuchBookException;
import com.vlad.libraryjparest.exception_handling.client_exception.ClientAlreadyExistsException;
import com.vlad.libraryjparest.exception_handling.client_exception.NoSuchClientException;
import com.vlad.libraryjparest.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl service;

    @Mock
    private ClientService clientService;

    private Book book;

    private int testId = 1;

    private Client client;

    private int pageNo = 1;
    private int pageSize = 3;

    @BeforeEach
    void setUp() {
        book = new Book("Test", "Test", 1);
        client = new Client("Test", "Test", new Date());
        book.setClient(client);
        book.setDateAcquired(new Date());
    }

    @AfterEach
    void tearDown() {
        client = null;
        book = null;
    }

    @Test
    void getAllBooks() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        service.getAllBooks(pageNo, pageSize);
        verify(repository).findAll(Pageable.ofSize(pageSize).withPage(pageNo));
    }

    @Test
    void addBook() {
        when(repository.existsBookByTitleAndAuthorAndPublished(any(),
                any(), anyInt())).thenReturn(false);
        service.addBook(book);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(repository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();
        assertEquals(capturedBook, book);
    }

    @Test
    void willThrowWhenBookExists() {
        when(repository.existsBookByTitleAndAuthorAndPublished(book.getTitle(),
                book.getAuthor(), book.getPublished())).thenReturn(true);
        BookAlreadyExistsException exception =
                assertThrows(BookAlreadyExistsException.class, ()-> service.addBook(book));
        assertEquals("Such book is already available", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void getBook() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.findById(anyInt())).thenReturn(Optional.ofNullable(book));
        Book found = service.getBook(anyInt());
        assertNotNull(found);
    }

    @Test
    void deleteBook() {
        when(repository.existsById(anyInt())).thenReturn(true);
        service.deleteBook(testId);
        verify(repository).deleteById(testId);
    }

    @Test
    void getBooksByTitle() {
        String title = "test";
        when(repository.findAllByTitle(any(), any())).thenReturn(List.of(book));
        service.getAllBooksByTitle(pageNo, pageSize, title);
        ArgumentCaptor<String> titleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findAllByTitle(titleArgumentCaptor.capture(), any(Pageable.class));
        String capturedTitle = titleArgumentCaptor.getValue();
        assertEquals(capturedTitle, title);
    }

    @Test
    void getBooksByAuthor() {
        String author = "test";
        when(repository.findByAuthor(any(), any())).thenReturn(List.of(book));
        service.getAllBooksByAuthor(pageNo, pageSize, author);
        ArgumentCaptor<String> authorArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByAuthor(authorArgumentCaptor.capture(), any(Pageable.class));
        String capturedAuthor = authorArgumentCaptor.getValue();
        assertEquals(capturedAuthor, author);
    }

    @Test
    void getAllBooksByAuthorAndTitle() {
        String title = "first";
        String author = "last";
        when(repository.findAllByAuthorAndTitle(any(), any(), any())).thenReturn(List.of(book));
        service.getAllBooksByAuthorAndTitle(pageNo, pageSize, title, author);
        ArgumentCaptor<String> titleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> authorArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findAllByAuthorAndTitle(titleArgumentCaptor.capture(),
                authorArgumentCaptor.capture(), any(Pageable.class));
        String capturedTitle = titleArgumentCaptor.getValue();
        String capturedAuthor = authorArgumentCaptor.getValue();
        assertEquals(capturedTitle, title);
        assertEquals(capturedAuthor, author);
    }

    @Test
    void updateBook(){
        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(new Book()));
        when(repository.save(any())).thenReturn(book);
        service.updateBook(book, anyInt());
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(repository).save(bookArgumentCaptor.capture());
        Book captured = bookArgumentCaptor.getValue();
        assertEquals(captured.getTitle(), book.getTitle());
        assertEquals(captured.getAuthor(), book.getAuthor());
        assertEquals(captured.getPublished(), book.getPublished());
    }

    @Test
    void assign(){
        when(repository.existsById(testId)).thenReturn(true);
        when(repository.findById(testId)).thenReturn(Optional.of(book));
        when(clientService.getClient(testId)).thenReturn(client);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        service.assign(testId, testId);
        verify(repository).save(bookArgumentCaptor.capture());
        Book captured = bookArgumentCaptor.getValue();
        assertEquals(captured.getClient(), client);
    }

    @Test
    void returnBook(){
        when(repository.existsById(testId)).thenReturn(true);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        when(repository.findById(testId)).thenReturn(Optional.of(book));
        service.returnBook(testId);
        verify(repository).save(bookArgumentCaptor.capture());
        Book captured = bookArgumentCaptor.getValue();
        assertNull(captured.getClient());
    }


    @Test
    void willThrowWhenBookDoesNotExists(){

        String test = "Test";

        when(repository.existsById(anyInt())).thenReturn(false);
        when(repository.findAllByAuthorAndTitle(any(), any(), any())).thenReturn(List.of());
        when(repository.findByAuthor(any(), any())).thenReturn(List.of());
        when(repository.findAllByTitle(any(), any())).thenReturn(List.of());

        NoSuchBookException exceptionWhenGet = assertThrows
                (NoSuchBookException.class, () -> service.getBook(testId));
        assertEquals("There is no book with id = " + testId, exceptionWhenGet.getMessage());

        NoSuchBookException exceptionWhenDelete = assertThrows
                (NoSuchBookException.class, () -> service.deleteBook(testId));
        assertEquals("There is no book with id = " + testId, exceptionWhenDelete.getMessage());
        verify(repository, never()).deleteById(anyInt());

        NoSuchBookException exceptionWhenNoAuthor = assertThrows
                (NoSuchBookException.class, () -> service.getAllBooksByAuthor(pageNo, pageSize, test));
        assertEquals("There is no book with author " + test, exceptionWhenNoAuthor.getMessage());

        NoSuchBookException exceptionWhenNoTitle = assertThrows
                (NoSuchBookException.class, () -> service.getAllBooksByTitle(pageNo, pageSize, test));
        assertEquals("There is no book with title " + test, exceptionWhenNoTitle.getMessage());

        NoSuchBookException exceptionWhenNoTitleAndAuthor = assertThrows
                (NoSuchBookException.class, () -> service.getAllBooksByAuthorAndTitle(pageNo, pageSize, test, test));
        assertEquals("There is no book with author " + test + " and title " + test, exceptionWhenNoTitleAndAuthor.getMessage());

        NoSuchBookException exceptionWhenUpdate = assertThrows
                (NoSuchBookException.class, () -> service.updateBook(book, testId));
        assertEquals("There is no book with id = " + testId, exceptionWhenUpdate.getMessage());
        verify(repository, never()).save(any());

        NoSuchBookException exceptionWhenAssign = assertThrows
                (NoSuchBookException.class, () -> service.assign(testId, testId));
        assertEquals("There is no book with id = " + testId, exceptionWhenAssign.getMessage());

        NoSuchBookException exceptionWhenReturn = assertThrows
                (NoSuchBookException.class, () -> service.returnBook(testId));
        assertEquals("There is no book with id = " + testId, exceptionWhenReturn.getMessage());
    }
}