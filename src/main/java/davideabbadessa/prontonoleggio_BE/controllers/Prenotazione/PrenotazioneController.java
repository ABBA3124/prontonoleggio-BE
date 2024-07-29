package davideabbadessa.prontonoleggio_BE.controllers.Prenotazione;

import davideabbadessa.prontonoleggio_BE.components.PrenotazioneSpecifications;
import davideabbadessa.prontonoleggio_BE.entities.prenotazioni.Prenotazione;
import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.TipoVeicolo;
import davideabbadessa.prontonoleggio_BE.payloads.Prenotazione.PrenotazioneDTO;
import davideabbadessa.prontonoleggio_BE.services.Prenotazione.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private PrenotazioneSpecifications prenotazioneSpecifications;

    // POST /prenotazioni/crea
    @PostMapping("/crea")
    public ResponseEntity<Prenotazione> creaPrenotazione(@Validated @RequestBody PrenotazioneDTO prenotazioneDTO) {
        Prenotazione prenotazione = prenotazioneService.creaPrenotazione(prenotazioneDTO);
        return new ResponseEntity<>(prenotazione, HttpStatus.CREATED);
    }

    // GET /prenotazioni/disponibilita
    @GetMapping("/disponibilita")
    public ResponseEntity<Boolean> verificaDisponibilita(
            @RequestParam UUID veicoloId,
            @RequestParam LocalDate dataInizio,
            @RequestParam LocalDate dataFine) {
        boolean disponibile = prenotazioneService.isVeicoloDisponibile(veicoloId, dataInizio, dataFine);
        return new ResponseEntity<>(disponibile, HttpStatus.OK);
    }

    // GET /prenotazioni/storico
    @GetMapping("/storico")
    public ResponseEntity<Page<Prenotazione>> getStoricoPrenotazioni(@AuthenticationPrincipal Utente currentAuthenticatedUtente,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniByUtente(currentAuthenticatedUtente.getId(), pageable);
        return new ResponseEntity<>(prenotazioni, HttpStatus.OK);
    }

    @PutMapping("/modifica/{id}")
    public ResponseEntity<Prenotazione> modificaPrenotazione(
            @PathVariable UUID id,
            @Validated @RequestBody PrenotazioneDTO prenotazioneDTO,
            @AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        Prenotazione prenotazione = prenotazioneService.modificaPrenotazione(id, prenotazioneDTO, currentAuthenticatedUtente);
        return new ResponseEntity<>(prenotazione, HttpStatus.OK);
    }

    @DeleteMapping("/cancella/{id}")
    public ResponseEntity<Void> cancellaPrenotazione(
            @PathVariable UUID id,
            @AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        prenotazioneService.cancellaPrenotazione(id, currentAuthenticatedUtente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tuttelaprenotazioni")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Page<Prenotazione> getTuttePrenotazioni(
            @RequestParam(required = false) LocalDateTime dataCreazione,
            @RequestParam(required = false) LocalDate dataInizio,
            @RequestParam(required = false) LocalDate dataFine,
            @RequestParam(required = false) String targaVeicolo,
            //-------------------------------------------------------
            @RequestParam(required = false) TipoVeicolo tipoVeicolo,
            @RequestParam(required = false) String posizioneVeicolo,
            //-------------------------------------------------------
            @RequestParam(required = false) String nomeUtente,
            @RequestParam(required = false) String cognomeUtente,
            @RequestParam(required = false) String emailUtente,
            @RequestParam(required = false) String telefonoUtente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Specification<Prenotazione> spec = Specification.where(null);

        if (dataCreazione != null) {
            spec = spec.and(PrenotazioneSpecifications.hasdataCreazione(dataCreazione));
        }
        if (dataInizio != null) {
            spec = spec.and(PrenotazioneSpecifications.hasdataInizio(dataInizio));
        }
        if (dataFine != null) {
            spec = spec.and(PrenotazioneSpecifications.hasdataFine(dataFine));
        }
        if (nomeUtente != null) {
            spec = spec.and(PrenotazioneSpecifications.hasNomeUtente(nomeUtente));
        }
        if (cognomeUtente != null) {
            spec = spec.and(PrenotazioneSpecifications.hasCognomeUtente(cognomeUtente));
        }
        if (emailUtente != null) {
            spec = spec.and(PrenotazioneSpecifications.hasEmailUtente(emailUtente));
        }
        if (telefonoUtente != null) {
            spec = spec.and(PrenotazioneSpecifications.hasTelefonoUtente(telefonoUtente));
        }
        if (tipoVeicolo != null) {
            spec = spec.and(PrenotazioneSpecifications.hasTipoVeicolo(tipoVeicolo));
        }
        if (posizioneVeicolo != null) {
            spec = spec.and(PrenotazioneSpecifications.hasPosizione(posizioneVeicolo));
        }
        if (targaVeicolo != null) {
            spec = spec.and(PrenotazioneSpecifications.hasTargaVeicolo(targaVeicolo));
        }


        return this.prenotazioneService.getAllPrenotazioniAdmin(spec, page, size, sortBy);
    }
}
