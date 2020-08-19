package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Author;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoAuthorsException;
import com.ksan.example.blackBooks.servingwebcontent.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public @ResponseBody
    Iterable<Author> getAll() {
        return authorService.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    Author getById(@PathVariable(value = "id") Integer id) throws NoAuthorsException {
        return authorService.findById(id);
    }

    @PostMapping()
    public @ResponseBody
    Author addNewAuthor(@RequestParam(name = "name") String name,
                        @RequestParam(name = "surname") String surname,
                        @RequestParam(name = "patronymic") String patronymic) {
        return authorService.addNewAuthor(name, surname, patronymic);
    }
}