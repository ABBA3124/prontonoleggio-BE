package davideabbadessa.prontonoleggio_BE.crontrollers;

import davideabbadessa.prontonoleggio_BE.entities.utente.Utente;
import davideabbadessa.prontonoleggio_BE.payloads.utente.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    // <-------------------------------------------- ROLE_USER -------------------------------------------->
    @GetMapping("/me")
    public Utente getProfilo(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;

    }

    @PutMapping("/me")
    public Utente updateProfilo(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @Validated @RequestBody NuovoUtenteDTO body) {
        return this.utenteService.updateProfilo(currentAuthenticatedUtente.getId(), body);
    }

    @DeleteMapping("/me")
    public void deleteProfilo(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        this.utenteService.deleteProfilo(currentAuthenticatedUtente.getId());
    }

    // <-------------------------------------------- ROLE_SUPERADMIN -------------------------------------------->
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.getAllUtenti(page, size, sortBy);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void deleteUtente(@PathVariable UUID id) {
        this.utenteService.deleteProfilo(id);
    }

    @GetMapping("/cerca/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Utente getUtenteById(@PathVariable UUID id) {
        return this.utenteService.getUtenteById(id);
    }

}

