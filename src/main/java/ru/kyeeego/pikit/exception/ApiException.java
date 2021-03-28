package ru.kyeeego.pikit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public ApiException(HttpStatus status) {
        this.status = status;
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
