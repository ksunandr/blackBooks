package com.ksan.example.blackBooks.servingwebcontent.exceptions;

public class NoBookException extends Exception {

    Integer id;

    public NoBookException(String message, Integer id) {
        super(message);
        this.id = id;
    }
}
