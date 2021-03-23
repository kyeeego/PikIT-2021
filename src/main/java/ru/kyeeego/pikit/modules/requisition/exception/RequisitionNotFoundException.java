package ru.kyeeego.pikit.modules.requisition.exception;

public class RequisitionNotFoundException extends RuntimeException {
    public RequisitionNotFoundException() {
        super("Requisition not found");
    }

    public RequisitionNotFoundException(String message) {
        super(message);
    }
}
