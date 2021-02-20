package com.api.phonebook.exception;

import org.springframework.http.HttpStatus;

public class PhoneBookException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PhoneBookException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        int statusCode = HttpStatus.NOT_FOUND.value();
        return String.valueOf(statusCode);
    }

    
}