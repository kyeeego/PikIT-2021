package ru.kyeeego.pikit.modules.files;

import org.springframework.http.HttpStatus;
import ru.kyeeego.pikit.exception.ApiException;

public class FileStorageException extends ApiException {
    public FileStorageException() {
        super(HttpStatus.INSUFFICIENT_STORAGE);
    }

    public FileStorageException(String message) {
        super(message, HttpStatus.INSUFFICIENT_STORAGE);
    }
}
