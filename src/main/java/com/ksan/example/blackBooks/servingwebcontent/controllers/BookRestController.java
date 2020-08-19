package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoAuthorsException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoBookException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.RunOutOfBooksException;
import com.ksan.example.blackBooks.servingwebcontent.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Book getBook(@PathVariable(value = "id") Integer id) throws NoBookException {
        return bookService.findById(id);
    }

    @GetMapping()
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    Book addNewBook(@RequestParam(name = "name") String name,
                    @RequestParam(name = "authors") List<Integer> authorIds,
                    @RequestParam(name = "qnt", required = false, defaultValue = "0") Integer qnt,
                    @RequestParam(name = "year") Integer year) throws NoAuthorsException {

        return bookService.addNewBook(name, authorIds, qnt, year);
    }

    @GetMapping("/filter")
    public @ResponseBody
    Iterable<Book> filter(@RequestParam(name = "name", required = false) String name,
                          @RequestParam(name = "leftYear", required = false) Integer leftYear,
                          @RequestParam(name = "rightYear", required = false) Integer rightYear,
                          @RequestParam(name = "authorName", required = false) String authorName,
                          @RequestParam(name = "status", required = false) Boolean status) {
        return bookService.filter(name, leftYear, rightYear, authorName, status);
    }


    @PostMapping(path = "/edit/{id}")
    public @ResponseBody
    Book updateBook(
            @PathVariable(value = "id") Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "authorsIds", required = false) List<Integer> authorIds,
            @RequestParam(name = "qnt", required = false) Integer qnt,
            @RequestParam(name = "year", required = false) Integer year) throws NoBookException, NoAuthorsException { //todo dto

        return bookService.updateBook(id, name, authorIds, qnt, year);
    }

    @PostMapping(path = "/buy")
    public @ResponseBody
    Book buyBook(@RequestParam(name = "id") Integer id) throws NoBookException, RunOutOfBooksException {
        return bookService.buyBook(id);
    }
}