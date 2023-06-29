package com.vlad.libraryjparest.repository;


import com.vlad.libraryjparest.entity.Book;
import com.vlad.libraryjparest.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
    public List<Client> findByFirstName(String name, Pageable pageable);
    public List<Client> findByLastName(String lastName, Pageable pageable);
    public List<Client> findByFirstNameAndLastName(String name, String lastName, Pageable pageable);
    public Boolean existsClientByFirstNameAndLastNameAndBirthday(String firstName, String lastName,
                                                              Date birthday);
    public Boolean existsClientById(int id);
}
