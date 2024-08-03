package davideabbadessa.prontonoleggio_BE.security;

import davideabbadessa.prontonoleggio_BE.exceptions.UnauthorizedException;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${SCADENZA_TOKEN}")
    private long SCADENZA_TOKEN;

    // Crea un token JWT con scadenza
    public String creaToken(Utente utente) {
        return Jwts.builder()
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + SCADENZA_TOKEN))
                   .subject(String.valueOf(utente.getId()))
                   .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                   .compact();
    }

    // Verifica la validità del token JWT
    public void verifyToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Token non valido");
        }
    }

    // Estrae l'ID dell'utente dal token JWT
    public String extractIdFromToken(String token) {
        return Jwts.parser()
                   .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }
}
