package ru.kyeeego.pikit.filter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kyeeego.pikit.exception.BadRequestException;
import ru.kyeeego.pikit.exception.ExpiredException;
import ru.kyeeego.pikit.exception.ForbiddenException;
import ru.kyeeego.pikit.exception.UnauthorizedException;
import ru.kyeeego.pikit.modules.user.exception.UserAlreadyExistsException;
import ru.kyeeego.pikit.modules.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionFilter {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        return defaultExceptionHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return defaultExceptionHandler(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequest(BadRequestException ex) {
        return defaultExceptionHandler(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return defaultExceptionHandler(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUsernameNotFound(UsernameNotFoundException ex) {
        return defaultExceptionHandler(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleBadCredentials(BadCredentialsException ex) {
        return defaultExceptionHandler(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleUnauthorized(UnauthorizedException ex) {
        return defaultExceptionHandler(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleForbidden(ForbiddenException ex) {
        return defaultExceptionHandler(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public ErrorResponse handleExpired(ExpiredException ex) {
        return defaultExceptionHandler(ex, HttpStatus.GONE);
    }

    // TODO: handle all JWT exceptions at once somehow

    private <E extends RuntimeException> ErrorResponse defaultExceptionHandler(E ex, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(status);
        return errorResponse;
    }
}