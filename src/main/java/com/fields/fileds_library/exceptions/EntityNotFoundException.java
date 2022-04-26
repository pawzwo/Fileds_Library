package com.fields.fileds_library.exceptions;

import org.springframework.http.HttpStatus;

public abstract class EntityNotFoundException extends AppException{

    public EntityNotFoundException(String message) {
        super(message + "not found.", HttpStatus.NOT_FOUND);
    }
}
