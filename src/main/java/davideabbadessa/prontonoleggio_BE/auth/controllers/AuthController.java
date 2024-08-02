package davideabbadessa.prontonoleggio_BE.auth.controllers;

import davideabbadessa.prontonoleggio_BE.auth.services.AuthService;
import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.utente.payloads.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteLoginDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteLoginResponseDTO;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteResponseDTO;
import davideabbadessa.prontonoleggio_BE.utente.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
