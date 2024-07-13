package davideabbadessa.prontonoleggio_BE.services;

import davideabbadessa.prontonoleggio_BE.entities.Utente;
import davideabbadessa.prontonoleggio_BE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    // Metodo per ottenere tutti gli utenti
    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    // Metodo per ottenere un utente tramite ID
    public Optional<Utente> getUtenteById(UUID id) {
        return utenteRepository.findById(id);
    }

    // Metodo per salvare o aggiornare un utente
    public Utente saveOrUpdateUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    // Metodo per eliminare un utente tramite ID
    public void deleteUtenteById(UUID id) {
        utenteRepository.deleteById(id);
    }
}

