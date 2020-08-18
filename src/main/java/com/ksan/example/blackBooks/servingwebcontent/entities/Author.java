package com.ksan.example.blackBooks.servingwebcontent.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, String surname, String patronymic, Set<Book> books) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        //this.books = books;
    }

    public Author(String name, String surname, String patronymic) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    private String name;
    private String surname;
    private String patronymic;

    @JsonIgnore
    @ManyToMany //todo??
    @JoinTable(
            name = "AUTHORS_BOOKS",
            inverseJoinColumns = {@JoinColumn(name = "author_id")},
            joinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> books = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
