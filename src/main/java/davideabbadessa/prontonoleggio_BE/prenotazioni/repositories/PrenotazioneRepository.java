package davideabbadessa.prontonoleggio_BE.prenotazioni.repositories;

import davideabbadessa.prontonoleggio_BE.prenotazioni.entities.Prenotazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID>, JpaSpecificationExecutor<Prenotazione> {
    @Query("SELECT p FROM Prenotazione p WHERE p.veicolo.id = :veicoloId AND p.dataFine >= :dataInizio AND p.dataInizio <= :dataFine")
    List<Prenotazione> findPrenotazioniSovrapposte(UUID veicoloId, LocalDate dataInizio, LocalDate dataFine);

    Page<Prenotazione> findByUtenteId(UUID utenteId, Pageable pageable);

    @Query("SELECT DISTINCT p.veicolo.id FROM Prenotazione p WHERE p.dataFine >= :dataInizio AND p.dataInizio <= :dataFine")
    List<UUID> findVeicoliNonDisponibili(LocalDate dataInizio, LocalDate dataFine);

    Page<Prenotazione> findAll(Pageable pageable);

}
