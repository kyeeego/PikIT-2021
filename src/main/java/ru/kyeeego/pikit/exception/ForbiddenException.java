package ru.kyeeego.pikit.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException {
    public ForbiddenException() {
        super("Forbidden", HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
