package com.vlad.libraryjparest.service;


import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import com.vlad.libraryjparest.exception_handling.book_exception.BookAlreadyExistsException;
import com.vlad.libraryjparest.exception_handling.book_exception.NoSuchBookException;
import com.vlad.libraryjparest.exception_handling.client_exception.NoSuchClientException;
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
    private ClientService clientService;

    @Override
    public List<Book> getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findAll(pageable).getContent();
        return books;
    }

    @Override
    public void addBook(Book book) {
        Boolean bookExists = bookRepository.existsBookByTitleAndAuthorAndPublished(book.getTitle(),
                book.getAuthor(), book.getPublished());
        if(bookExists) throw new BookAlreadyExistsException("Such book is already available");
        bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book, int id) {
        if(!bookRepository.existsById(id))
            throw new NoSuchBookException("There is no book with id = " + id);
        return bookRepository.findById(id).map(persisted -> {
            persisted.setAuthor(book.getAuthor());
            persisted.setTitle(book.getTitle());
            persisted.setPublished(book.getPublished());
            return bookRepository.save(persisted);
        }).get();
    }

    @Override
    public Book getBook(int id) {
        if(!bookRepository.existsById(id))
            throw new NoSuchBookException("There is no book with id = " + id);
        return bookRepository.findById(id).get();
    }

    @Override
    public void deleteBook(int id) {
        if(!bookRepository.existsById(id))
            throw new NoSuchBookException("There is no book with id = " + id);
        bookRepository.deleteById(id);
    }

    @Override
    public Book assign(int bookId, int clientId) {
        if(!bookRepository.existsById(bookId))
            throw new NoSuchBookException("There is no book with id = " + bookId);
        Book book = getBook(bookId);
        Client client = clientService.getClient(clientId);
        book.setClient(client);
        book.setDateAcquired(new Date());
        return bookRepository.save(book);
    }

    @Override
    public Book returnBook(int id) {
        if(!bookRepository.existsById(id))
            throw new NoSuchBookException("There is no book with id = " + id);
        Book book = getBook(id);
        book.setClient(null);
        book.setDateAcquired(null);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooksByAuthorAndTitle(int pageNo, int pageSize,
                                                  String author, String title) {
        if(author != null && title == null) return getAllBooksByAuthor(pageNo, pageSize, author);
        else if(title != null && author == null) return getAllBooksByTitle(pageNo, pageSize, title);
        else if(title == null) return getAllBooks(pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findAllByAuthorAndTitle(author, title, pageable);
        if(books.isEmpty()) throw new NoSuchBookException("There is no book with author " +
                author + " and title " + title);
        return books;
    }

    @Override
    public List<Book> getAllBooksByAuthor(int pageNo, int pageSize, String author) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findByAuthor(author, pageable);
        if(books.isEmpty()) throw new NoSuchBookException("There is no book with author " + author);
        return books;
    }

    @Override
    public List<Book> getAllBooksByTitle(int pageNo, int pageSize, String title) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> books = bookRepository.findAllByTitle(title, pageable);
        if(books.isEmpty()) throw new NoSuchBookException("There is no book with title " + title);
        return books;
    }
}
