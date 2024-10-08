package davideabbadessa.prontonoleggio_BE.recensioni.repositories;

import davideabbadessa.prontonoleggio_BE.recensioni.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUtenteId(UUID utenteId);

    Optional<Review> findByPrenotazioneId(UUID prenotazioneId);

    List<Review> findAllByOrderByDataCreazioneDesc();

    List<Review> findAllByUtenteIdOrderByDataCreazioneDesc(UUID utenteId);
}
