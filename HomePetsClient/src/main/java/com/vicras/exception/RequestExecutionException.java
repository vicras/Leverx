package com.vicras.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * @author viktar hraskou
 */
public class RequestExecutionException extends RuntimeException {
    public RequestExecutionException(String message) {
        super(message);
    }
}
