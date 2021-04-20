package com.vicras.exception;

public class CodeNotFoundException extends RuntimeException{
    public CodeNotFoundException() {
        super();
    }

    public CodeNotFoundException(String message) {
        super(message);
    }
}
