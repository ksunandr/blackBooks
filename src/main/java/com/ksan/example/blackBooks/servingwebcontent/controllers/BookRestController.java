package com.ksan.example.blackBooks.servingwebcontent.controllers;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoBookException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.RunOutOfBooksException;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;

@Controller
@RequestMapping("/rest")
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @GetMapping()
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }



  ///for terminal curl http://localhost:8080/rest/15
    @GetMapping(path = "/{id}")
    public @ResponseBody
    Book getBook(@PathVariable(value = "id") Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    ///for terminal  curl http://localhost:8080/rest/add -d name=111 -d author=111 -d
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
            @RequestParam(name = "year", required = false) Integer year) throws NoBookException { //todo dto

        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(id));

        if (name != null) book.setName(name);
        if (author != null) book.setAuthor(author);
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

        }
        finally { //?
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