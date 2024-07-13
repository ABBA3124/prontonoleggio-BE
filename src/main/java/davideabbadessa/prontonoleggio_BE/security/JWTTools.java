package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.security;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.User;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;


    public String creaToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);

        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Per favore effettua di nuovo il login!");

        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}