package com.ksan.example.blackBooks.servingwebcontent.services;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import org.springframework.data.domain.Page;

public interface BookService {

    Page<Book> findByName(String name);


    Page<Book> filter(String name, String authorName);
}
