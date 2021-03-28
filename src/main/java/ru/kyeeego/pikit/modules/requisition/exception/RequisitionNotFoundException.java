package ru.kyeeego.pikit.modules.requisition.exception;

import org.springframework.http.HttpStatus;
import ru.kyeeego.pikit.exception.ApiException;

public class RequisitionNotFoundException extends ApiException {
    public RequisitionNotFoundException() {
        super("Requisition not found", HttpStatus.NOT_FOUND);
    }

    public RequisitionNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
