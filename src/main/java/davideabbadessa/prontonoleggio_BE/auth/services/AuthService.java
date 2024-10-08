package davideabbadessa.prontonoleggio_BE.auth.services;

import davideabbadessa.prontonoleggio_BE.exceptions.UnauthorizedException;
import davideabbadessa.prontonoleggio_BE.security.JWTTools;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.utente.payloads.UtenteLoginDTO;
import davideabbadessa.prontonoleggio_BE.utente.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;

    public String authUtenteAndGenerateToken(UtenteLoginDTO utenteLoginDTO) {
        Utente utente = this.utenteService.getUtenteByEmail(utenteLoginDTO.email());
        if (passwordEncoder.matches(utenteLoginDTO.password(), utente.getPassword())) {
            return jwtTools.creaToken(utente);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }
}
