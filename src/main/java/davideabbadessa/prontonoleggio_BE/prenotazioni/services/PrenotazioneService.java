package davideabbadessa.prontonoleggio_BE.prenotazioni.services;

import davideabbadessa.prontonoleggio_BE.Mailgun.MailgunService;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.prenotazioni.entities.Prenotazione;
import davideabbadessa.prontonoleggio_BE.prenotazioni.payload.PrenotazioneDTO;
import davideabbadessa.prontonoleggio_BE.prenotazioni.repositories.PrenotazioneRepository;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.utente.repositories.UtenteRepository;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Veicolo;
import davideabbadessa.prontonoleggio_BE.veicolo.repositories.VeicoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private VeicoloRepository veicoloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private MailgunService mailgunService;

    public Prenotazione creaPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        Veicolo veicolo = veicoloRepository.findById(prenotazioneDTO.veicoloId())
                                           .orElseThrow(() -> new NotFoundException("Veicolo non trovato"));
        Utente utente = utenteRepository.findById(prenotazioneDTO.utenteId())
                                        .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        if (!isVeicoloDisponibile(veicolo.getId(), prenotazioneDTO.dataInizio(), prenotazioneDTO.dataFine())) {
            throw new BadRequestException("Veicolo non disponibile per le date selezionate");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setVeicolo(veicolo);
        prenotazione.setUtente(utente);
        prenotazione.setDataCreazione(LocalDateTime.now());
        prenotazione.setDataInizio(prenotazioneDTO.dataInizio());
        prenotazione.setDataFine(prenotazioneDTO.dataFine());

        // Salvataggio della prenotazione nel database
        Prenotazione prenotazioneSalvata = prenotazioneRepository.save(prenotazione);

        // Invio dell'email di conferma
        mailgunService.sendPrenotazioneConfermaEmail(utente, prenotazioneSalvata);

        return prenotazioneSalvata;
    }

    public boolean isVeicoloDisponibile(UUID veicoloId, LocalDate dataInizio, LocalDate dataFine) {
        List<Prenotazione> prenotazioniSovrapposte = prenotazioneRepository.findPrenotazioniSovrapposte(veicoloId, dataInizio, dataFine);
        return prenotazioniSovrapposte.isEmpty();
    }

    public Page<Prenotazione> getPrenotazioniByUtente(UUID utenteId, Pageable pageable) {
        return prenotazioneRepository.findByUtenteId(utenteId, pageable);
    }

    public Prenotazione modificaPrenotazione(UUID id, PrenotazioneDTO prenotazioneDTO, Utente currentAuthenticatedUtente) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                                                          .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUtente()
                         .getId()
                         .equals(currentAuthenticatedUtente.getId()) && !currentAuthenticatedUtente.getAuthorities()
                                                                                                   .contains(new SimpleGrantedAuthority("ROLE_SUPERADMIN"))) {
            throw new BadRequestException("Non sei autorizzato a modificare questa prenotazione");
        }

        if (!isVeicoloDisponibile(prenotazione.getVeicolo()
                                              .getId(), prenotazioneDTO.dataInizio(), prenotazioneDTO.dataFine())) {
            throw new BadRequestException("Veicolo non disponibile per le nuove date selezionate");
        }

        prenotazione.setDataInizio(prenotazioneDTO.dataInizio());
        prenotazione.setDataFine(prenotazioneDTO.dataFine());

        return prenotazioneRepository.save(prenotazione);
    }

    public void cancellaPrenotazione(UUID id, Utente currentAuthenticatedUtente) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                                                          .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));
        if (!prenotazione.getUtente()
                         .getId()
                         .equals(currentAuthenticatedUtente.getId()) && !currentAuthenticatedUtente.getAuthorities()
                                                                                                   .contains(new SimpleGrantedAuthority("ROLE_SUPERADMIN"))) {
            throw new BadRequestException("Non sei autorizzato a cancellare questa prenotazione");
        }
        prenotazioneRepository.delete(prenotazione);
    }

    public Page<Prenotazione> getAllPrenotazioniAdmin(Specification<Prenotazione> spec, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return prenotazioneRepository.findAll(spec, pageable);
    }
}