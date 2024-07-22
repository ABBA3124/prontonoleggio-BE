package davideabbadessa.prontonoleggio_BE.controllers.Prenotazione;

import davideabbadessa.prontonoleggio_BE.entities.prenotazioni.Prenotazione;
import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import davideabbadessa.prontonoleggio_BE.payloads.Prenotazione.PrenotazioneDTO;
import davideabbadessa.prontonoleggio_BE.services.Prenotazione.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

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
    public ResponseEntity<List<Prenotazione>> getStoricoPrenotazioni(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        List<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniByUtente(currentAuthenticatedUtente.getId());
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
}
