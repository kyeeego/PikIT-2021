package ru.kyeeego.pikit.exception;

import org.springframework.http.HttpStatus;

public class ExpiredException extends ApiException {
    public ExpiredException() {
        super("Expired", HttpStatus.GONE);
    }

    public ExpiredException(String message) {
        super(message, HttpStatus.GONE);
    }
}
