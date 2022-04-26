package com.fields.fileds_library.exceptions;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("User ");
    }
}
