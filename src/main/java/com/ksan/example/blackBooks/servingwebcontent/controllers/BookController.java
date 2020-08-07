package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller()
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping()
    public String mainPage(String name, Map<String, Object> model) {
        Iterable<Book> books = bookRepository.findAll();

        model.put("books", books);
        return "books";
    }

    @PostMapping(path = "/add")   // @RequestParam means it is a parameter from the GET or POST request
    public String addNewBook(@RequestParam(name = "name") String name,
                             @RequestParam(name = "author") String author,
                             @RequestParam(name = "qnt", required = false, defaultValue = "0") Integer qnt,
                             @RequestParam(name = "year") Integer year,
                             Map<String, Object> model) {
        Book book = new Book(name);
        book.setAuthor(author);
        book.setInStock(qnt);
        book.setPublicationYear(year);
        bookRepository.save(book);

        Iterable<Book> books = bookRepository.findAll();
        model.put("books", books);
        return "books";
    }

    @GetMapping(path = "/{id}")
    public String getBook(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        Iterable<Book> books = Collections.singletonList(bookRepository.findById(id).orElse(null));
        model.put("books", books);
        return "books";
    }


}