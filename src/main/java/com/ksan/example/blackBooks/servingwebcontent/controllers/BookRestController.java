package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoBookException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.RunOutOfBooksException;
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
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Book getBook(@PathVariable(value = "id") Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    Book addNewBook(@RequestParam(name = "name") String name,
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

    @PostMapping(path = "/edit/{id}")
    public @ResponseBody
    Book updateBook(
            @PathVariable(value = "id") Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "qnt", required = false) Integer qnt,
            @RequestParam(name = "year", required = false) Integer year) throws NoBookException {

        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(id));

        if (name != null) book.setName(name);
        if (author != null) book.setAuthor(author);
        if (qnt != null) book.setInStock(qnt);
        if (year != null) book.setPublicationYear(year);
        bookRepository.save(book);

        return book;
    }


    @PostMapping(path = "/buy")
    public @ResponseBody
    Book buyBook( @RequestParam(name = "id") Integer id) throws NoBookException, RunOutOfBooksException {

        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(id));
        if (book.getInStock() <= 0) throw new RunOutOfBooksException(id);

        book.setInStock(book.getInStock() - 1);

        bookRepository.save(book);

        return book;
    }
}