package com.vicras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Forbidden add comment to admin")
public class ForbiddenAddCommentToAdminException extends RuntimeException {
    public ForbiddenAddCommentToAdminException() {
        super();
    }

    public ForbiddenAddCommentToAdminException(String message) {
        super(message);
    }
}
