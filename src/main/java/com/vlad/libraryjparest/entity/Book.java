package com.vlad.libraryjparest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vlad.libraryjparest.util.ExpirationCalculator;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "books")
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "published")
    private int published;

    @Column(name = "date_acquired")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date dateAcquired;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties("books")
    private Client client;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean expired;

    public Book() {
    }

    public Book(String title, String author, int published, Date dateAcquired) {
        this.title = title;
        this.author = author;
        this.published = published;
        this.dateAcquired = dateAcquired;
    }


    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
