package davideabbadessa.prontonoleggio_BE.prenotazioni.components;

import davideabbadessa.prontonoleggio_BE.prenotazioni.entities.Prenotazione;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.TipoVeicolo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PrenotazioneSpecifications {

    public static Specification<Prenotazione> hasdataCreazione(LocalDateTime dataCreazione) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataCreazione"), dataCreazione);
    }

    public static Specification<Prenotazione> hasdataInizio(LocalDate dataInizio) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataInizio"), dataInizio);
    }

    public static Specification<Prenotazione> hasdataFine(LocalDate dataFine) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataFine"), dataFine);
    }

    public static Specification<Prenotazione> hasNomeUtente(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("utente")
                                          .get("nome"), nome);
    }

    public static Specification<Prenotazione> hasCognomeUtente(String cognome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("utente")
                                          .get("cognome"), cognome);
    }

    public static Specification<Prenotazione> hasEmailUtente(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("utente")
                                          .get("email"), email);
    }

    public static Specification<Prenotazione> hasTelefonoUtente(String telefono) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("utente")
                                          .get("telefono"), telefono);
    }

    public static Specification<Prenotazione> hasTargaVeicolo(String targa) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("veicolo")
                                          .get("targa"), targa);
    }

    public static Specification<Prenotazione> hasPosizione(String posizione) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("veicolo")
                                          .get("nomeSede"), posizione);
    }

    public static Specification<Prenotazione> hasTipoVeicolo(TipoVeicolo tipoVeicolo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("veicolo")
                                          .get("tipoVeicolo"), tipoVeicolo);
    }
}
