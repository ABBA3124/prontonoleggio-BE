package davideabbadessa.prontonoleggio_BE.exceptions;

import java.time.LocalDateTime;

public record ErroriDTO(String message, LocalDateTime time) {
}