package davideabbadessa.prontonoleggio_BE.auth.controllers;

import davideabbadessa.prontonoleggio_BE.auth.services.AuthService;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.utente.payloads.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteLoginDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteLoginResponseDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteResponseDTO;
import davideabbadessa.prontonoleggio_BE.utente.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO utenteLoginDTO) {
        return new UtenteLoginResponseDTO(authService.authUtenteAndGenerateToken(utenteLoginDTO));
    }


    @PostMapping("/registrati")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponseDTO creaUtente(@Validated @RequestBody NuovoUtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new UtenteResponseDTO(this.utenteService.saveUtente(body)
                                                       .getId());
    }

    @GetMapping("/conferma")
    public ResponseEntity<String> confermaEmail(@RequestParam String token) {
        Utente utente = utenteService.getUtenteByToken(token);
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Token non valido");
        }

        utente.setEmailConfirmed(true);
        utente.setConfirmationToken(null); // Rimuovi il token dopo la conferma
        utenteService.updateUtente(utente);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:5173/"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
