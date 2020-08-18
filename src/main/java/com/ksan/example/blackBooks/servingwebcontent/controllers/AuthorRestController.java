package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Author;
import com.ksan.example.blackBooks.servingwebcontent.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorRestController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping()
    public @ResponseBody
    Iterable<Author> getAll() {
        return authorRepository.findAll();
    }

    ///for terminal curl http://localhost:8080/rest/15
    @GetMapping(path = "/{id}")
    public @ResponseBody
    Author getById(@PathVariable(value = "id") Integer id) {
        return authorRepository.findById(id).orElse(null);
    }

    ///for terminal  curl http://localhost:8080/author/add -d name=111 -d surname=111 -d patronymic=111

    @PostMapping(path = "/add")
    public @ResponseBody
    Author addNewAuthor(@RequestParam(name = "name") String name,
                        @RequestParam(name = "surname") String surname,
                        @RequestParam(name = "patronymic") String patronymic) {
        Author author = new Author(name, surname, patronymic);
        authorRepository.save(author);
        return author;
    }
}