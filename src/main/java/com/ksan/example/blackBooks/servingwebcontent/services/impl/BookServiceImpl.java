package com.ksan.example.blackBooks.servingwebcontent.services.impl;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookJpaRepository;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookSpecs;
import com.ksan.example.blackBooks.servingwebcontent.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookJpaRepository bookRepository;


    @Override
    public Page<Book> findByName(String name) {
        Pageable firstPageWithThreeElementssortedByName =
                PageRequest.of(0, 3, Sort.by("name"));

        return bookRepository.findByName(name, firstPageWithThreeElementssortedByName);
    }

@Override
    public Page<Book> filter(String name, String authorName) {
        Pageable firstPageWithFiveElementssortedByName =
                PageRequest.of(0, 5, Sort.by("name"));

      return bookRepository.findAll(Specification.where(BookSpecs.byName(name).and(BookSpecs.byAuthorName(authorName))), firstPageWithFiveElementssortedByName);
       // return bookRepository.findAll(Specification.where(), firstPageWithThreeElementssortedByName);
    }

}
