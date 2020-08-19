package com.ksan.example.blackBooks.servingwebcontent.exceptions;

public class RunOutOfBooksException extends Exception {

    private Integer id;

    public RunOutOfBooksException(String massage, Integer id) {
        super(massage);
        this.id = id;
    }
}
