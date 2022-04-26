package com.fields.fields_library.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("User ");
    }
}
