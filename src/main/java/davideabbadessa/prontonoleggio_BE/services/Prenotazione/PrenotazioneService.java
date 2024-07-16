package davideabbadessa.prontonoleggio_BE.services.Prenotazione;

import davideabbadessa.prontonoleggio_BE.entities.prenotazioni.Prenotazione;
import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import davideabbadessa.prontonoleggio_BE.entities.veicolo.Veicolo;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.payloads.Prenotazione.PrenotazioneDTO;
import davideabbadessa.prontonoleggio_BE.repositories.Prenotazione.PrenotazioneRepository;
import davideabbadessa.prontonoleggio_BE.repositories.UtenteRepository;
import davideabbadessa.prontonoleggio_BE.repositories.VeicoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private VeicoloRepository veicoloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Prenotazione creaPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        Veicolo veicolo = veicoloRepository.findById(prenotazioneDTO.veicoloId())
                .orElseThrow(() -> new NotFoundException("Veicolo non trovato"));
        Utente utente = utenteRepository.findById(prenotazioneDTO.utenteId())
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setVeicolo(veicolo);
        prenotazione.setUtente(utente);
        prenotazione.setDataInizio(prenotazioneDTO.dataInizio());
        prenotazione.setDataFine(prenotazioneDTO.dataFine());

        return prenotazioneRepository.save(prenotazione);
    }
}
