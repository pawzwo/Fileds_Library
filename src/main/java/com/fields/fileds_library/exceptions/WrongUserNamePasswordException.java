package com.fields.fileds_library.exceptions;

import org.springframework.http.HttpStatus;

public class WrongUserNamePasswordException extends AppException{
    public WrongUserNamePasswordException() {
        super("Wrong User Name or Password", HttpStatus.UNAUTHORIZED);
    }
}
