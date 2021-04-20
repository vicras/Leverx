package com.vicras.exception;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
        super();
    }

    public UserNotExistException(String message) {
        super(message);
    }
}
