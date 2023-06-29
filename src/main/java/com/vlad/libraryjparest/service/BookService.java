package com.vlad.libraryjparest.service;



import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    public List<Book> getAllBooks(int pageNo, int pageSize);
    public void addBook(Book book);
    public Book getBook(int id);
    public void deleteBook(int id);
    public Book assign(int bookId, int clientId);
    public Book returnBook(int id);

    public List<Book> getAllBooksByAuthorAndTitle(int pageNo, int pageSize,
                                                  String author, String title);
    public List<Book> getAllBooksByAuthor(int pageNo, int pageSize, String author);
    public List<Book> getAllBooksByTitle(int pageNo, int pageSize, String title);

}
