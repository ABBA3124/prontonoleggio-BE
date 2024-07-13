package davideabbadessa.prontonoleggio_BE.exceptions;


import java.util.UUID;


public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("ID: --> " + id + " non trovato!");
    }

    public NotFoundException(String messaggio) {
        super(messaggio);
    }
}