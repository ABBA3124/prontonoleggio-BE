package davideabbadessa.prontonoleggio_BE.repositories;

import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID>, JpaSpecificationExecutor<Utente> {

    Optional<Utente> findByEmail(String email);

    Optional<Utente> findByUsername(String username);

    Optional<Utente> findByPatente(String patente);

    Optional<Utente> findByCodiceFiscale(String codiceFiscale);

    Optional<Utente> findByTelefono(String telefono);

}
