package davideabbadessa.prontonoleggio_BE.utente.repositories;

import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID>, JpaSpecificationExecutor<Utente> {

    // Cerca Per Email
    Optional<Utente> findByEmail(String email);

    // Cerca Per Username
    Optional<Utente> findByUsername(String username);

    // Cerca Per Patente
    Optional<Utente> findByPatente(String patente);

    // Cerca Per Codice Fiscale
    Optional<Utente> findByCodiceFiscale(String codiceFiscale);

    // Cerca Per Telefono
    Optional<Utente> findByTelefono(String telefono);

    // Cerca Per Token di Conferma Email (per la conferma dell'account)
    Utente findByConfirmationToken(String confirmationToken);

}
