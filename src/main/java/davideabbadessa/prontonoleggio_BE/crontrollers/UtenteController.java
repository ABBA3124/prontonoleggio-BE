package davideabbadessa.prontonoleggio_BE.crontrollers;

import davideabbadessa.prontonoleggio_BE.entities.Utente;
import davideabbadessa.prontonoleggio_BE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    // Get all users
    @GetMapping
    public List<Utente> getAllUtenti() {
        return utenteService.getAllUtenti();
    }

    // Get a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtenteById(@PathVariable UUID id) {
        Optional<Utente> utente = utenteService.getUtenteById(id);
        return utente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Save a new user
    @PostMapping
    public Utente saveUtente(@RequestBody Utente utente) {
        return utenteService.saveOrUpdateUtente(utente);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<Utente> updateUtente(@PathVariable UUID id, @RequestBody Utente utente) {
        return utenteService.getUtenteById(id)
                .map(existingUtente -> {
                    utente.setId(id);
                    Utente updatedUtente = utenteService.saveOrUpdateUtente(utente);
                    return ResponseEntity.ok(updatedUtente);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtenteById(@PathVariable UUID id) {
        return utenteService.getUtenteById(id)
                .map(utente -> {
                    utenteService.deleteUtenteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

