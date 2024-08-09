package davideabbadessa.prontonoleggio_BE.utente.services;

import davideabbadessa.prontonoleggio_BE.Mailgun.MailgunService;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.utente.payloads.ModificaUtenteDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.utente.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailgunService mailgunService;

    // <-------------------------------------------- Get all Utenti -------------------------------------------->
    public Page<Utente> getAllUtenti(Specification<Utente> spec, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return utenteRepository.findAll(spec, pageable);
    }

    // <-------------------------------------------- Utente by ID -------------------------------------------->
    public Utente getUtenteById(UUID id) {
        return utenteRepository.findById(id)
                               .orElseThrow(() -> new NotFoundException(id));
    }

    // <-------------------------------------------- Utente by Email -------------------------------------------->
    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email)
                               .orElseThrow(() -> new NotFoundException(email + " not found"));
    }

    // <-------------------------------------------- Utente by Username  -------------------------------------------->
    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByUsername(username)
                               .orElseThrow(() -> new NotFoundException(username + " not found"));
    }

    // <-------------------------------------------- Save Utente -------------------------------------------->
    public Utente saveUtente(NuovoUtenteDTO utenteDTO) {
        validateUtenteControlSave(null, utenteDTO);

        //Creazione dell'utente
        Utente nuovoUtente = new Utente(utenteDTO);
        nuovoUtente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + nuovoUtente.getNome() + "+" + nuovoUtente.getCognome() + "&background=1E90FF&color=fff");

        //Genera il token di conferma
        String token = UUID.randomUUID()
                           .toString();
        nuovoUtente.setConfirmationToken(token);
        nuovoUtente.setEmailConfirmed(false); // Inizialmente l'email non è confermata

        //Salvataggio dell'utente nel database
        Utente utenteSalvato = utenteRepository.save(nuovoUtente);

        //Invio email di conferma
        String subject = "Conferma la tua registrazione";
        String text = "Benvenuto su Pronto Noleggio " + nuovoUtente.getNome() + ",\n\n" +
                "Grazie per esserti registrato. Per favore conferma il tuo indirizzo email cliccando sul seguente link:\n" +
                "http://localhost:12000/auth/conferma?token=" + token;

        mailgunService.sendEmail(utenteSalvato.getEmail(), subject, text);

        return utenteSalvato;
    }

    public Utente getUtenteByToken(String token) {
        return utenteRepository.findByConfirmationToken(token);
    }

    public void updateUtente(Utente utente) {
        utenteRepository.save(utente);
    }


    // <-------------------------------------------- Validazione Utente Controllo Per Save -------------------------------------------->
    private void validateUtenteControlSave(UUID userId, NuovoUtenteDTO utenteDTO) {
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

    // <-------------------------------------------- Update Utente  -------------------------------------------->
    public Utente updateProfilo(UUID utenteId, ModificaUtenteDTO body) {
        validateUtenteControlUpdate(utenteId, body);
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

    // <-------------------------------------------- Validazione Utente Controllo Per Update -------------------------------------------->
    private void validateUtenteControlUpdate(UUID userId, ModificaUtenteDTO utenteDTO) {
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

    // <-------------------------------------------- Verify Password Utente  -------------------------------------------->
    private boolean verifyPassword(Utente utente, String password) {
        return passwordEncoder.matches(password, utente.getPassword());
    }

    // <-------------------------------------------- Delete Utente ROLE_USER && ROLE_SUPERADMIN (Profilo Personale) -------------------------------------------->
    public void deleteProfilo(UUID utenteId, String password) {
        Utente utente = getUtenteById(utenteId);

        if (verifyPassword(utente, password)) {
            utenteRepository.delete(utente);
        } else {
            throw new BadRequestException("Password errata.");
        }
    }

    // <-------------------------------------------- Delete Utente SUPERADMIN (Profilo di un altro Utente) -------------------------------------------->
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


