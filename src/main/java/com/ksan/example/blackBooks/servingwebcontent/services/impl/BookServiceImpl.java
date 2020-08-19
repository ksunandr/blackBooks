package com.ksan.example.blackBooks.servingwebcontent.services.impl;

import com.ksan.example.blackBooks.servingwebcontent.constants.Messages;
import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoAuthorsException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoBookException;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.RunOutOfBooksException;
import com.ksan.example.blackBooks.servingwebcontent.repositories.BookJpaRepository;
import com.ksan.example.blackBooks.servingwebcontent.services.AuthorService;
import com.ksan.example.blackBooks.servingwebcontent.services.BookService;
import com.ksan.example.blackBooks.servingwebcontent.specification.BookSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookJpaRepository bookRepository;

    private final AuthorService authorService;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookServiceImpl(BookJpaRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public Book findById(Integer id) throws NoBookException {
        return bookRepository.findById(id).orElseThrow(() -> new NoBookException(Messages.BOOK_WITH_SUCH_ID_DOESNT_EXIST, id));
    }

    @Override
    public Book addNewBook(String name, List<Integer> authorIds, Integer qnt, Integer year) throws NoAuthorsException {
        Book book = new Book(name);
        book.setAuthors(authorService.getAuthors(authorIds));
        book.setInStock(qnt);
        book.setPublicationYear(year);
        bookRepository.save(book);
        return book;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> filter(String name, Integer leftYear, Integer rightYear, String authorName, Boolean status) {
        Pageable firstPageWithFiveElementsSortedByName =
                PageRequest.of(0, 5, Sort.by("name"));
      return bookRepository.findAll(
              Specification
                      .where(BookSpecs.nameIs(name)
                              .and(BookSpecs.afterYear(leftYear))
                              .and(BookSpecs.rightYear(rightYear))
                              .and(BookSpecs.authorNameIs(authorName))
                              .and(BookSpecs.inStock(status))
                      )
              ,firstPageWithFiveElementsSortedByName);
      }

    @Override
    public Book updateBook(Integer id, String name, List<Integer> authorIds, Integer qnt, Integer year) throws NoBookException, NoAuthorsException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoBookException(Messages.BOOK_WITH_SUCH_ID_DOESNT_EXIST, id));
        if (name != null) book.setName(name);
        if (authorIds != null) book.setAuthors(authorService.getAuthors(authorIds));
        if (qnt != null) book.setInStock(qnt);
        if (year != null) book.setPublicationYear(year);
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book buyBook(Integer id) throws NoBookException, RunOutOfBooksException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id, LockModeType.PESSIMISTIC_READ);

        try {
            if (book == null) throw new NoBookException(Messages.BOOK_WITH_SUCH_ID_DOESNT_EXIST, id);
            if (book.getInStock() <= 0) throw new RunOutOfBooksException(Messages.RUN_OUT_OF_BOOKS_WITH_SUCH_ID, id);
            book.setInStock(book.getInStock() - 1);
            entityManager.getTransaction().commit();
            entityManager.close();

        } finally {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
        }
        return book;
    }
}
