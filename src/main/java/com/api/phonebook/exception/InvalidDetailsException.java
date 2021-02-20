package com.api.phonebook.exception;

import org.springframework.http.HttpStatus;

public class InvalidDetailsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidDetailsException(String message) {
        super(message);
    }

    public static String InvalidEmailAddress() {
        int statusCode = HttpStatus.BAD_REQUEST.value();
        return String.valueOf(statusCode);
    }
    
}
