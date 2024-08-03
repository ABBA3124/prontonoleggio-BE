package davideabbadessa.prontonoleggio_BE.exceptions;


import java.util.UUID;


public class NotFoundException extends RuntimeException {

    // <-------- NOT FOUND 404 -------->

    public NotFoundException(UUID id) {
        super("Utente non trovato, effettua nuovamente il login");
    }

    public NotFoundException(String messaggio) {
        super(messaggio);
    }
}