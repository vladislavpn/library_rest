package com.vlad.libraryjparest.service;


import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.util.ExpirationCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vlad.libraryjparest.repository.BookRepository;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExpirationCalculator expirationCalculator;

    @Override
    public List<Book> getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findAll(pageable).getContent();
        books.forEach(book -> expirationCalculator.calculate(book));
        return books;
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBook(int id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Client getOwner(int id) {
        return bookRepository.findById(id).get().getClient();
    }

    @Override
    public void assign(Book book, Client client) {
        book.setClient(client);
        bookRepository.save(book);
    }

    @Override
    public void release(Book book) {
        book.setClient(null);
        bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooksByAuthorAndTitle(int pageNo, int pageSize,
                                                  String author, String title) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findAllByAuthorAndTitle(author, title, pageable);
        books.forEach(book -> expirationCalculator.calculate(book));
        return books;
    }

    @Override
    public List<Book> getAllBooksByAuthor(int pageNo, int pageSize, String author) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findByAuthor(author, pageable);
        books.forEach(book -> expirationCalculator.calculate(book));
        return books;
    }

    @Override
    public List<Book> getAllBooksByTitle(int pageNo, int pageSize, String title) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findAllByTitle(title, pageable);
        books.forEach(book -> expirationCalculator.calculate(book));
        return books;
    }
}
