package davideabbadessa.prontonoleggio_BE.payloads.Prenotazione;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDTO(
        UUID id,
        UUID veicoloId,
        UUID utenteId,
        LocalDate dataInizio,
        LocalDate dataFine) {
}
