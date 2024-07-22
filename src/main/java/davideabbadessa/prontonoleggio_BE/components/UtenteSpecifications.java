package davideabbadessa.prontonoleggio_BE.components;

import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UtenteSpecifications {

    public static Specification<Utente> hasNome(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Utente> hasCognome(String cognome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")), "%" + cognome.toLowerCase() + "%");
    }

    public static Specification<Utente> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Utente> hasTelefono(String telefono) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("telefono")), "%" + telefono.toLowerCase() + "%");
    }

    public static Specification<Utente> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<Utente> hasRole(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<Utente> hasEta(Integer eta) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("eta"), eta);
    }

    public static Specification<Utente> hasCitta(String citta) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("citta")), "%" + citta.toLowerCase() + "%");
    }

    public static Specification<Utente> hasProvincia(String provincia) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("provincia")), "%" + provincia.toLowerCase() + "%");
    }

    public static Specification<Utente> hasNazione(String nazione) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nazione")), "%" + nazione.toLowerCase() + "%");
    }

    public static Specification<Utente> hasDataNascita(LocalDate dataNascita) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataNascita"), dataNascita);
    }

    public static Specification<Utente> hasCodiceFiscale(String codiceFiscale) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("codiceFiscale")), "%" + codiceFiscale.toUpperCase() + "%");
    }

    public static Specification<Utente> hasPatente(String patente) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("patente")), "%" + patente.toLowerCase() + "%");
    }
}
