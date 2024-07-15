package davideabbadessa.prontonoleggio_BE.services;

import davideabbadessa.prontonoleggio_BE.entities.Utente;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.payloads.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Utente> getAllUtenti(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Utente getUtenteById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email + " not found"));
    }

    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(username + " not found"));
    }


    public Utente saveUtente(NuovoUtenteDTO utenteDTO) {
        if (utenteRepository.findByEmail(utenteDTO.email()).isPresent()) {
            throw new BadRequestException("Email: " + utenteDTO.email() + " già in uso");
        }
        if (utenteRepository.findByUsername(utenteDTO.username()).isPresent()) {
            throw new BadRequestException("Username: " + utenteDTO.username() + " già in uso");
        }
        if (utenteRepository.findByPatente(utenteDTO.patente()).isPresent()) {
            throw new BadRequestException("Patente: " + utenteDTO.patente() + " già in uso");
        }
        if (utenteRepository.findByCodiceFiscale(utenteDTO.codiceFiscale()).isPresent()) {
            throw new BadRequestException("Codice Fiscale: " + utenteDTO.codiceFiscale() + " già in uso");
        }
        if (utenteRepository.findByTelefono(utenteDTO.telefono()).isPresent()) {
            throw new BadRequestException("Numero di telefono: " + utenteDTO.telefono() + " già in uso");
        }

        Utente nuovoUtente = new Utente(utenteDTO);
        nuovoUtente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + nuovoUtente.getNome() + "+" + nuovoUtente.getCognome() + "&background=random&color=fff");
        return utenteRepository.save(nuovoUtente);
    }
}


