package davideabbadessa.prontonoleggio_BE.crontrollers;

import davideabbadessa.prontonoleggio_BE.exceptions.BadRequestException;
import davideabbadessa.prontonoleggio_BE.payloads.NuovoUtenteDTO;
import davideabbadessa.prontonoleggio_BE.payloads.UtenteLoginDTO;
import davideabbadessa.prontonoleggio_BE.payloads.UtenteLoginResponseDTO;
import davideabbadessa.prontonoleggio_BE.payloads.UtenteResponseDTO;
import davideabbadessa.prontonoleggio_BE.services.AuthService;
import davideabbadessa.prontonoleggio_BE.services.UtenteService;
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
        return new UtenteResponseDTO(this.utenteService.saveUtente(body).getId());
    }
}
