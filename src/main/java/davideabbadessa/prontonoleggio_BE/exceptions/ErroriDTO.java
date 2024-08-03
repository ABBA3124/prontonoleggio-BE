package davideabbadessa.prontonoleggio_BE.exceptions;

import java.time.LocalDateTime;

// DTO per la gestione degli errori da restituire al client in caso di eccezioni sollevate dal server

public record ErroriDTO(String message, LocalDateTime time) {
}