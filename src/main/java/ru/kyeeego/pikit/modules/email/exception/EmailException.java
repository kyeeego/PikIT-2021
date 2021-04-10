package ru.kyeeego.pikit.modules.email.exception;

import org.springframework.http.HttpStatus;
import ru.kyeeego.pikit.exception.ApiException;

public class EmailException extends ApiException {
    public EmailException() {
        super("Error Sending email", HttpStatus.SERVICE_UNAVAILABLE);
    }

    public EmailException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
