package com.vicras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Does not belong to you")
public class UserNotOwnerException extends RuntimeException {
    public UserNotOwnerException() {
        super();
    }

    public UserNotOwnerException(String message) {
        super(message);
    }
}
