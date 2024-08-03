package davideabbadessa.prontonoleggio_BE.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionsHendler {

    // <-------- BAD REQUEST 400 -------->
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroriDTO handleBadRequest(BadRequestException error) {
        if (error.getErrorsList() != null) {
            String message = error.getErrorsList()
                                  .stream()
                                  .map(objectError -> objectError.getDefaultMessage())
                                  .collect(Collectors.joining(". "));
            return new ErroriDTO(message, LocalDateTime.now());
        } else {
            return new ErroriDTO(error.getMessage(), LocalDateTime.now());
        }
    }

    // <-------- UNAUTHORIZED 401 -------->
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroriDTO handleUnauthorized(UnauthorizedException errore) {
        return new ErroriDTO(errore.getMessage(), LocalDateTime.now());
    }

    // <-------- FORBIDDEN 403 -------->
    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroriDTO handleForbidden(AuthorizationDeniedException ex) {
        return new ErroriDTO("Non hai accesso a questa funzionalità", LocalDateTime.now());
    }

    // <-------- NOT FOUND 404 -------->
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroriDTO handleNotFound(NotFoundException errore) {
        return new ErroriDTO(errore.getMessage(), LocalDateTime.now());
    }

    // <-------- INTERNAL SERVER ERROR 500 -------->
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroriDTO handleGeneric(Exception errore) {
        return new ErroriDTO("Internal Server Error -> Errore Generico! ", LocalDateTime.now());
    }


}