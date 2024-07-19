package davideabbadessa.prontonoleggio_BE.services;

import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.payloads.utente.ModificaUtenteDTO;
import davideabbadessa.prontonoleggio_BE.payloads.utente.NuovoUtenteDTO;
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

    // <-------------------------------------------- Metodi -------------------------------------------->
    public Page<Utente> getAllUtenti(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Utente getUtenteById(UUID id) {
        return utenteRepository.findById(id)
                               .orElseThrow(() -> new NotFoundException(id));
    }

    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email)
                               .orElseThrow(() -> new NotFoundException(email + " not found"));
    }

    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByUsername(username)
                               .orElseThrow(() -> new NotFoundException(username + " not found"));
    }

    // <-------------------------------------------- Salva Utente  -------------------------------------------->
    public Utente saveUtente(NuovoUtenteDTO utenteDTO) {
        validateUtente(null, utenteDTO);
        Utente nuovoUtente = new Utente(utenteDTO);
        nuovoUtente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + nuovoUtente.getNome() + "+" + nuovoUtente.getCognome() + "&background=1E90FF&color=fff");
        return utenteRepository.save(nuovoUtente);
    }

    // <-------------------------------------------- Update Utente  -------------------------------------------->
    public Utente updateProfilo(UUID utenteId, ModificaUtenteDTO body) {
        validateUtente2(utenteId, body);
        Utente found = this.getUtenteById(utenteId);

        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setTelefono(body.telefono());
        found.setIndirizzo(body.indirizzo());
        found.setNumeroCivico(body.numeroCivico());
        found.setCitta(body.citta());
        found.setCap(body.cap());
        found.setProvincia(body.provincia());
        found.setNazione(body.nazione());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        return utenteRepository.save(found);
    }

    // <-------------------------------------------- Validazione Utente  -------------------------------------------->
    private void validateUtente(UUID userId, NuovoUtenteDTO utenteDTO) {
        if (utenteRepository.findByEmail(utenteDTO.email())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Email: " + utenteDTO.email() + " già in uso");
        }
        if (utenteRepository.findByUsername(utenteDTO.username())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Username: " + utenteDTO.username() + " già in uso");
        }
        if (utenteRepository.findByPatente(utenteDTO.patente())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Patente: " + utenteDTO.patente() + " già in uso");
        }
        if (utenteRepository.findByCodiceFiscale(utenteDTO.codiceFiscale())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Codice Fiscale: " + utenteDTO.codiceFiscale() + " già in uso");
        }
        if (utenteRepository.findByTelefono(utenteDTO.telefono())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Numero di telefono: " + utenteDTO.telefono() + " già in uso");
        }
    }

    // <-------------------------------------------- Validazione Utente  -------------------------------------------->
    private void validateUtente2(UUID userId, ModificaUtenteDTO utenteDTO) {
        if (utenteRepository.findByEmail(utenteDTO.email())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Email: " + utenteDTO.email() + " già in uso");
        }
        if (utenteRepository.findByUsername(utenteDTO.username())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Username: " + utenteDTO.username() + " già in uso");
        }
        if (utenteRepository.findByTelefono(utenteDTO.telefono())
                            .filter(existingUser -> !existingUser.getId()
                                                                 .equals(userId))
                            .isPresent()) {
            throw new BadRequestException("Numero di telefono: " + utenteDTO.telefono() + " già in uso");
        }
    }

    // <-------------------------------------------- ROLE_USER  -------------------------------------------->

    private boolean verifyPassword(Utente utente, String password) {
        return passwordEncoder.matches(password, utente.getPassword());
    }

    // <-------------------------------------------- Delete Utente ROLE_USER  -------------------------------------------->
    public void deleteProfilo(UUID utenteId, String password) {
        Utente utente = getUtenteById(utenteId);

        if (verifyPassword(utente, password)) {
            utenteRepository.delete(utente);
        } else {
            throw new BadRequestException("Password errata.");
        }
    }

    // <-------------------------------------------- Delete Utente ROLE_SUPERADMIN  -------------------------------------------->
    public void deleteProfiloAdmin(UUID superAdminId, String password, UUID targetUserId) {
        Utente superAdmin = getUtenteById(superAdminId);
        Utente targetUser = getUtenteById(targetUserId);

        if (verifyPassword(superAdmin, password)) {
            utenteRepository.delete(targetUser);
        } else {
            throw new BadRequestException("Password errata.");
        }
    }

}


