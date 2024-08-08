package davideabbadessa.prontonoleggio_BE.recensioni.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReviewDTO(
        UUID utenteId,

        UUID prenotazioneId,

        @Min(1) @Max(5)
        int rating,

        @NotEmpty
        @Size(min = 1, max = 30)
        String titolo,

        @NotEmpty
        String commento
) {
}
