package com.vlad.libraryjparest.service;



import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    public List<Book> getAllBooks(int pageNo, int pageSize);
    public void saveBook(Book book);
    public Book getBook(int id);
    public void deleteBook(int id);
    public Client getOwner(int id);
    public void assign(Book book, Client client);
    public void release(Book book);

    public List<Book> getAllBooksByAuthorAndTitle(int pageNo, int pageSize,
                                                  String author, String title);
    public List<Book> getAllBooksByAuthor(int pageNo, int pageSize, String author);
    public List<Book> getAllBooksByTitle(int pageNo, int pageSize, String title);

}
