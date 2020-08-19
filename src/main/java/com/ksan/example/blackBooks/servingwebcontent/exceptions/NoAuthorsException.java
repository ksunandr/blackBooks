package com.ksan.example.blackBooks.servingwebcontent.exceptions;

import java.util.List;

public class NoAuthorsException extends Exception {

    private List<Integer> ids;

    public NoAuthorsException(String message, List<Integer> ids) {
        super(message);
        this.ids = ids;
    }
}
