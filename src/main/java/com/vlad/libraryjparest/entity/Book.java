package com.vlad.libraryjparest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vlad.libraryjparest.util.ExpirationCalculator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "books")
@NoArgsConstructor
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    @Getter @Setter private int id;

    @Column(name = "title")
    @Getter @Setter private String title;

    @Column(name = "author")
    @Getter @Setter private String author;

    @Column(name = "published")
    @Getter @Setter private int published;

    @Column(name = "date_acquired")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Getter @Setter private Date dateAcquired;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties("books")
    @Getter @Setter private Client client;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter @Setter private Boolean expired;


    public Book(String title, String author, int published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public Book(String title, String author, int published, Date dateAcquired) {
        this.title = title;
        this.author = author;
        this.published = published;
        this.dateAcquired = dateAcquired;
    }

}
