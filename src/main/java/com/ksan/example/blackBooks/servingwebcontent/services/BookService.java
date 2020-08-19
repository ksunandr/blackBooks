package com.ksan.example.blackBooks.servingwebcontent.services;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoAuthorsException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoBookException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.RunOutOfBooksException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    Iterable<Book> findAll();

    Book findById(Integer name) throws NoBookException;

    Book addNewBook(String name, List<Integer> authorIds, Integer qnt, Integer year) throws NoAuthorsException;

    Page<Book> filter(String name, Integer leftYear, Integer rightYear, String authorName, Boolean status);

    Book updateBook(Integer id, String name, List<Integer> authorIds, Integer qnt, Integer year) throws NoBookException, NoAuthorsException;

    Book buyBook(Integer id) throws NoBookException, RunOutOfBooksException;
}
