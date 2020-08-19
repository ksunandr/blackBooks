package com.ksan.example.blackBooks.servingwebcontent.services.impl;

import com.ksan.example.blackBooks.servingwebcontent.constants.Messages;
import com.ksan.example.blackBooks.servingwebcontent.entities.Author;
import com.ksan.example.blackBooks.servingwebcontent.exceptions.NoAuthorsException;
import com.ksan.example.blackBooks.servingwebcontent.repositories.AuthorRepository;
import com.ksan.example.blackBooks.servingwebcontent.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAuthors(List<Integer> ids) throws NoAuthorsException {
        List<Author> authors = authorRepository.findByIdIn(ids);
        if (authors.isEmpty()) throw new NoAuthorsException(Messages.AUTHOR_WITH_SUCH_ID_DOESNT_EXIST, ids);
        return authors;
    }

    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author addNewAuthor(String name, String surname, String patronymic) {
        Author author = new Author(name, surname, patronymic);
        authorRepository.save(author);
        return author;
    }

    @Override
    public Author findById(Integer id) throws NoAuthorsException {
        return authorRepository.findById(id).orElseThrow(() -> new NoAuthorsException(Messages.BOOK_WITH_SUCH_ID_DOESNT_EXIST, id));
    }
}
