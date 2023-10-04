package com.vlad.libraryjparest.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@ToString
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;

    @Column(name = "first_name")
    @Getter @Setter private String firstName;

    @Column(name = "last_name")
    @Getter @Setter private String lastName;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Getter @Setter private Date birthday;

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties("client")
    @Getter @Setter private List<Book> books;

    public Client(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }


}
