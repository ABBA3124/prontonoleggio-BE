package davideabbadessa.prontonoleggio_BE.prenotazioni.payload;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDTO(
        UUID veicoloId,
        UUID utenteId,
        LocalDate dataInizio,
        LocalDate dataFine) {
}
