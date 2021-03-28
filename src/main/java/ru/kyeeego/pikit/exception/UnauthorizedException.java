package ru.kyeeego.pikit.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException() {
        super("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
