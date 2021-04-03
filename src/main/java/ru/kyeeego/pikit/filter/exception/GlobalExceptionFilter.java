package ru.kyeeego.pikit.filter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kyeeego.pikit.exception.*;
import ru.kyeeego.pikit.modules.files.FileStorageException;
import ru.kyeeego.pikit.modules.requisition.exception.RequisitionNotFoundException;
import ru.kyeeego.pikit.modules.user.exception.UserAlreadyExistsException;
import ru.kyeeego.pikit.modules.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionFilter {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();

        return runtimeExceptionHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequest(BadRequestException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUsernameNotFound(UsernameNotFoundException ex) {
        return runtimeExceptionHandler(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleBadCredentials(BadCredentialsException ex) {
        return runtimeExceptionHandler(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleUnauthorized(UnauthorizedException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleForbidden(ForbiddenException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(ExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public ErrorResponse handleExpired(ExpiredException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ResponseBody
    public ErrorResponse handleFileStorageEx(FileStorageException ex) {
        return defaultExceptionHandler(ex);
    }

    @ExceptionHandler(RequisitionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleReqNotFound(RequisitionNotFoundException ex) {
        return defaultExceptionHandler(ex);
    }

    private <E extends ApiException> ErrorResponse defaultExceptionHandler(E ex) {
        ex.printStackTrace();
        return new ErrorResponse(ex.getStatus(), ex.getMessage());
    }

    private <E extends RuntimeException> ErrorResponse runtimeExceptionHandler(E ex, HttpStatus status) {
        ex.printStackTrace();
        return new ErrorResponse(status, ex.getMessage());
    }
}