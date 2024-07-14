package davideabbadessa.prontonoleggio_BE.services;

import davideabbadessa.prontonoleggio_BE.entities.Utente;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.payloads.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo per ottenere tutti gli utenti presenti nel database
    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    // metodo cerca utente per id e se non lo trova lancia un'eccezione NotFoundException con l'id dell'utente non trovato
    public Utente getUtenteById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // metodo cerca utente per email e se non lo trova lancia un'eccezione NotFoundException con l'email dell'utente non trovato
    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email + " not found"));
    }

    // metodo cerca utente per username e se non lo trova lancia un'eccezione NotFoundException con l'username dell'utente non trovato
    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(username + " not found"));
    }

    // metodo per salvare un utente nel database e ritornare l'utente salvato con l'id generato dal database e la password criptata con BCrypt (passwordEncoder)
    public Utente saveUtente(NuovoUtenteDTO utenteDTO) {
        if (utenteRepository.findByEmail(utenteDTO.email()).isEmpty()) {
            Utente nuovoUtente = new Utente(
                    utenteDTO.nome(),
                    utenteDTO.cognome(),
                    utenteDTO.eta(),
                    utenteDTO.sesso(),
                    utenteDTO.username(),
                    utenteDTO.email(),
                    passwordEncoder.encode(utenteDTO.password()),
                    utenteDTO.telefono(),
                    utenteDTO.indirizzo(),
                    utenteDTO.numeroCivico(),
                    utenteDTO.citta(),
                    utenteDTO.cap(),
                    utenteDTO.provincia(),
                    utenteDTO.nazione(),
                    utenteDTO.dataNascita(),
                    utenteDTO.codiceFiscale(),
                    utenteDTO.patente()
            );
            return utenteRepository.save(nuovoUtente);
        } else {
            throw new BadRequestException(utenteDTO.email() + "già in uso");
        }
    }

}

