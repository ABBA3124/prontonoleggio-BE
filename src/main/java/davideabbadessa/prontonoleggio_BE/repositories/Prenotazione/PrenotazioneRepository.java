package davideabbadessa.prontonoleggio_BE.repositories.Prenotazione;

import davideabbadessa.prontonoleggio_BE.entities.prenotazioni.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    @Query("SELECT p FROM Prenotazione p WHERE p.veicolo.id = :veicoloId AND p.dataFine >= :dataInizio AND p.dataInizio <= :dataFine")
    List<Prenotazione> findPrenotazioniSovrapposte(UUID veicoloId, LocalDate dataInizio, LocalDate dataFine);
}
