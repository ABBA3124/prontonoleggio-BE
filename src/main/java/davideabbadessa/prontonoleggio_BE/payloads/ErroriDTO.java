package davideabbadessa.prontonoleggio_BE.payloads;

import java.time.LocalDateTime;

public record ErroriDTO(String message, LocalDateTime time) {
}