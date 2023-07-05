package com.vlad.libraryjparest.controller;

import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.service.BookService;
import com.vlad.libraryjparest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    public List<Book> allBooks(@RequestParam(required = false) String author,
                               @RequestParam(required = false) String title,
                               @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<Book> books;
        if(author != null && title != null) books = bookService
                .getAllBooksByAuthorAndTitle(pageNo, pageSize, author, title);
        else if(author != null) books = bookService.getAllBooksByAuthor(pageNo, pageSize, author);
        else if(title != null) books = bookService.getAllBooksByTitle(pageNo, pageSize, title);
        else books = bookService.getAllBooks(pageNo, pageSize);
        return books;
    }

    @PostMapping("/books")
    public Book addNewBook(@RequestBody Book book) {
        bookService.addBook(book);
        return book;
    }

    @GetMapping("/books/{id}")
    public Book bookInfo(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @DeleteMapping("books/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "Book with id " + id + " was successfully deleted";
    }

    @PatchMapping("/books/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable int id) {
        return bookService.updateBook(book, id);
    }


    @PatchMapping("books/{id}/return")
    public Book returnBook(@PathVariable int id) {
        return bookService.returnBook(id);
    }

    @PatchMapping("books/{bookId}/assign/{clientId}")
    public Book assignBook(@PathVariable int bookId, @PathVariable int clientId) {
        return bookService.assign(bookId, clientId);
    }
}

