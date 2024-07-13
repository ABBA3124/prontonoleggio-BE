package davideabbadessa.prontonoleggio_BE.repositories;

import davideabbadessa.prontonoleggio_BE.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {

    Utente findByEmail(String email);

    Utente findByUsername(String username);
}
