package com.searchitemsapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConfilctFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConfilctFoundException(String message){
        super(message);
    }
}