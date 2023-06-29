package com.vlad.libraryjparest.repository;


import com.vlad.libraryjparest.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findAllByAuthorAndTitle(String author, String title, Pageable pageable);
    public List<Book> findAllByTitle(String title, Pageable pageable);
    public List<Book> findByAuthor(String author, Pageable pageable);

    public Boolean existsBookByTitleAndAuthorAndPublished(String title, String author, int published);

}
