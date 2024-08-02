package davideabbadessa.prontonoleggio_BE.recensioni.repositorie;

import davideabbadessa.prontonoleggio_BE.recensioni.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUtenteId(UUID utenteId);
}
