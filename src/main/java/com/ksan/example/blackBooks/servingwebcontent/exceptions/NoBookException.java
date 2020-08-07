package com.ksan.example.blackBooks.servingwebcontent.exceptions;

public class NoBookException extends Exception {

    public NoBookException(Integer id) {
        super("Book with id = " + id.toString()+" doesn't exist");
    }
}
