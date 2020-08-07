package com.ksan.example.blackBooks.servingwebcontent.exceptions;

public class RunOutOfBooksException extends Exception {

    public RunOutOfBooksException(Integer id) {
        super("Run out of books with id = " + id.toString());
    }
}
