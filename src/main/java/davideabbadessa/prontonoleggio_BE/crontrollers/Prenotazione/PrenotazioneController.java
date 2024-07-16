package davideabbadessa.prontonoleggio_BE.crontrollers.Prenotazione;

import davideabbadessa.prontonoleggio_BE.entities.prenotazioni.Prenotazione;
import davideabbadessa.prontonoleggio_BE.payloads.Prenotazione.PrenotazioneDTO;
import davideabbadessa.prontonoleggio_BE.services.Prenotazione.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("/crea")
    public ResponseEntity<Prenotazione> creaPrenotazione(@Validated @RequestBody PrenotazioneDTO prenotazioneDTO) {
        Prenotazione prenotazione = prenotazioneService.creaPrenotazione(prenotazioneDTO);
        return new ResponseEntity<>(prenotazione, HttpStatus.CREATED);
    }
}
