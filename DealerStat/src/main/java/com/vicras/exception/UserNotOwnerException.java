package com.vicras.exception;

public class UserNotOwnerException extends RuntimeException{
    public UserNotOwnerException() {
        super();
    }

    public UserNotOwnerException(String message) {
        super(message);
    }
}
