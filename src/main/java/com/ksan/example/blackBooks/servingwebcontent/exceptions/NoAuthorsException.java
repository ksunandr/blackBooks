package com.ksan.example.blackBooks.servingwebcontent.exceptions;

import java.util.Collections;
import java.util.List;

public class NoAuthorsException extends Exception {

    private List<Integer> ids;

    public NoAuthorsException(String message, List<Integer> ids) {
        super(message);
        this.ids = ids;
    }

    public NoAuthorsException(String message, Integer id) {
        super(message);
        this.ids = Collections.singletonList(id);
    }
}
