package com.ksan.example.blackBooks.servingwebcontent;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping()
    public String startPage(String name, Model model) {
        model.addAttribute("name", "World");
        return "greeting";
    }

    @PostMapping(path="/add")   // @RequestParam means it is a parameter from the GET or POST request
    public String addNewUser(@RequestParam String name,
                       Map<String, Object> model) {
        Book book = new Book(name);
        bookRepository.save(book);

        //Iterable<Book> books = bookRepository.findAll();

//        model.put("messages", books);
//        model.put("start_info", "start_info");

        return "greeting";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Book> getAllUsers() {
        return bookRepository.findAll();
    }


}