package com.ksan.example.blackBooks.servingwebcontent.services;

import com.ksan.example.blackBooks.servingwebcontent.entities.Author;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoAuthorsException;

import java.util.List;

public interface AuthorService {

    List<Author> getAuthors(List<Integer> ids) throws NoAuthorsException;

    Iterable<Author> findAll();

    Author addNewAuthor(String name, String surname, String patronymic);

    Author findById(Integer id) throws NoAuthorsException;
}
