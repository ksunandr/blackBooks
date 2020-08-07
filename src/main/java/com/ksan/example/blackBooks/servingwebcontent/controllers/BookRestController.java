package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/add")
    public @ResponseBody Book addNewBook(@RequestParam(name = "name") String name,
                             @RequestParam(name = "author") String author,
                             @RequestParam(name = "qnt", required = false, defaultValue = "0") Integer qnt,
                             @RequestParam(name = "year") Integer year) {
        Book book = new Book(name);
        book.setAuthor(author);
        book.setInStock(qnt);
        book.setPublicationYear(year);
        bookRepository.save(book);
        return book;
    }

}