package davideabbadessa.prontonoleggio_BE.controllers;

import davideabbadessa.prontonoleggio_BE.components.UtenteSpecifications;
import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.payloads.utente.ModificaUtenteDTO;
import davideabbadessa.prontonoleggio_BE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private UtenteSpecifications utenteSpecifications;


    // <-------------------------------------------- ROLE_USER -------------------------------------------->
    @GetMapping("/me")
    public Utente getProfilo(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;

    }

    @PutMapping("/me")
    public Utente updateProfilo(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @Validated @RequestBody ModificaUtenteDTO body) {
        return this.utenteService.updateProfilo(currentAuthenticatedUtente.getId(), body);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteProfilo(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody Map<String, String> body) {
        String password = body.get("password");
        try {
            utenteService.deleteProfilo(currentAuthenticatedUtente.getId(), password);
            return ResponseEntity.ok("Profilo eliminato con successo");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Autenticazione fallita: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Errore del server: Si è verificato un problema durante l'eliminazione del profilo.");
        }
    }


    // <-------------------------------------------- ROLE_SUPERADMIN -------------------------------------------->
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Page<Utente> getAllUtenti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Integer eta,
            @RequestParam(required = false) String citta,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String nazione,
            @RequestParam(required = false) LocalDate dataNascita,
            @RequestParam(required = false) String codiceFiscale,
            @RequestParam(required = false) String patente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Specification<Utente> spec = Specification.where(null);

        if (nome != null) {
            spec = spec.and(UtenteSpecifications.hasNome(nome));
        }
        if (cognome != null) {
            spec = spec.and(UtenteSpecifications.hasCognome(cognome));
        }
        if (email != null) {
            spec = spec.and(UtenteSpecifications.hasEmail(email));
        }
        if (telefono != null) {
            spec = spec.and(UtenteSpecifications.hasTelefono(telefono));
        }
        if (username != null) {
            spec = spec.and(UtenteSpecifications.hasUsername(username));
        }
        if (role != null) {
            spec = spec.and(UtenteSpecifications.hasRole(role));
        }
        if (eta != null) {
            spec = spec.and(UtenteSpecifications.hasEta(eta));
        }
        if (citta != null) {
            spec = spec.and(UtenteSpecifications.hasCitta(citta));
        }
        if (provincia != null) {
            spec = spec.and(UtenteSpecifications.hasProvincia(provincia));
        }
        if (nazione != null) {
            spec = spec.and(UtenteSpecifications.hasNazione(nazione));
        }
        if (dataNascita != null) {
            spec = spec.and(UtenteSpecifications.hasDataNascita(dataNascita));
        }
        if (codiceFiscale != null) {
            spec = spec.and(UtenteSpecifications.hasCodiceFiscale(codiceFiscale));
        }
        if (patente != null) {
            spec = spec.and(UtenteSpecifications.hasPatente(patente));
        }

        return this.utenteService.getAllUtenti(spec, page, size, sortBy);
    }


    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<String> deleteProfiloById(@PathVariable UUID id, @AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody Map<String, String> body) {
        String password = body.get("password");
        try {
            utenteService.deleteProfiloAdmin(currentAuthenticatedUtente.getId(), password, id);
            return ResponseEntity.ok("Profilo eliminato con successo");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(e.getMessage());
        }
    }

    @GetMapping("/cerca/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Utente getUtenteById(@PathVariable UUID id) {
        return this.utenteService.getUtenteById(id);
    }

}

