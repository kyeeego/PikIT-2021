package ru.kyeeego.pikit.modules.user.exception;

import org.springframework.http.HttpStatus;
import ru.kyeeego.pikit.exception.ApiException;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException() {
        super("User with this email already exists", HttpStatus.CONFLICT);
    }

    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
