package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Author;
import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoBookException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.RunOutOfBooksException;
import com.ksan.example.blackBooks.servingwebcontent.repositories.AuthorRepository;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookRepository;
import com.ksan.example.blackBooks.servingwebcontent.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private BookService bookService;

    ///for terminal curl http://localhost:8080/rest/15
    @GetMapping(path = "/{id}")
    public @ResponseBody
    Book getBook(@PathVariable(value = "id") Integer id) {
        return bookRepository.findById(id).orElse(null);
    }//todo??? null??

    @GetMapping()
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }


   // @PostMapping("/filter")//todo??get for post curl http://localhost:8080/book/filter -d name=0000
    @GetMapping("/filter")//curl localhost:8080/book/filter?name=1
    public @ResponseBody
    Iterable<Book> filter(@RequestParam(name = "name", required = false) String name,
                          @RequestParam(name = "authorName", required = false) String authorName
                         // @RequestParam(name = "authorId", required = false) Integer authorId,
                         // @RequestParam(name = "status", required = false) Boolean status
    ) {

        return bookService.filter(name, authorName);
    }



    ///for terminal  curl http://localhost:8080/book/add -d name=bookname -d authors=27,28 -d qnt=1 -d year=1111
    @PostMapping(path = "/add")
    public @ResponseBody
    Book addNewBook(@RequestParam(name = "name") String name,
                    @RequestParam(name = "authors") List<Integer> authorIds,
                    @RequestParam(name = "qnt", required = false, defaultValue = "0") Integer qnt,
                    @RequestParam(name = "year") Integer year) {
        Book book = new Book(name);
        book.setAuthors(getAuthors(authorIds));
        book.setInStock(qnt);
        book.setPublicationYear(year);
        bookRepository.save(book);
        return book;
    }

    public List<Author> getAuthors(List<Integer> ids) {

        return authorRepository.findByIdIn(ids);

    }

    @PostMapping(path = "/edit/{id}")
    public @ResponseBody
    Book updateBook(
            @PathVariable(value = "id") Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "authors", required = false) List<Integer> authorIds,
            @RequestParam(name = "qnt", required = false) Integer qnt,
            @RequestParam(name = "year", required = false) Integer year) throws NoBookException { //todo dto

        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(id));

        if (name != null) book.setName(name);
        if (authorIds != null) book.setAuthors(getAuthors(authorIds));
        if (qnt != null) book.setInStock(qnt);
        if (year != null) book.setPublicationYear(year);
        bookRepository.save(book);

        return book;
    }


    @PostMapping(path = "/buyFast")
    public @ResponseBody
    Book buyBookFast(@RequestParam(name = "id") Integer id) throws NoBookException, RunOutOfBooksException {
        EntityManager entityManager = entityManagerFactory.createEntityManager(); //todo move to service
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id, LockModeType.PESSIMISTIC_READ);

        try {
            if (book == null) throw new NoBookException(id);
            if (book.getInStock() <= 0) throw new RunOutOfBooksException(id); //todo??


            book.setInStock(book.getInStock() - 1);

            entityManager.getTransaction().commit();
            entityManager.close();

        } finally { //?
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
        }

        return book;
    }

// @Autowired
// private LockRegistry lockRegistry; //??
//
//
//    @PostMapping(path = "/buySlow")
//    public @ResponseBody
//    Book buyBookSlow(@RequestParam(name = "id") Integer id) throws NoBookException, RunOutOfBooksException {
//        Lock lock = lockRegistry.obtain(id);
//        lock.lock(); //todo
//        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(id));
//        if (book.getInStock() <= 0) throw new RunOutOfBooksException(id);
//     try {
//         //lock
//
//         book.setInStock(book.getInStock() - 1);
//         bookRepository.save(book);
//         Thread.sleep(10000);//some business logic
//
//
//     }catch (InterruptedException e) {
//
//         Thread.currentThread().interrupt();
//     }
//     finally {
//         lock.unlock();
//     }
//
//
//        return book;
//    }
}