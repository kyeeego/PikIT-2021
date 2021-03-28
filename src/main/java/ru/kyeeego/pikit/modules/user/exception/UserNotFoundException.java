package ru.kyeeego.pikit.modules.user.exception;

import org.springframework.http.HttpStatus;
import ru.kyeeego.pikit.exception.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
