package com.ksan.example.blackBooks.servingwebcontent.repositories;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends CrudRepository<Book, Integer> {

    List<Book> findByName(String name);
    List<Book> findByNameStartingWith(String name);

    @Override
    Optional<Book> findById(Integer integer);
}
