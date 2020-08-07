package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping()
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

   @GetMapping(path="/{id}")
    public @ResponseBody Book getBook(@PathVariable(value = "id") Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

}