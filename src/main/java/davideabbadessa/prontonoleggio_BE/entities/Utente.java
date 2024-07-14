package davideabbadessa.prontonoleggio_BE.entities;

import davideabbadessa.prontonoleggio_BE.enums.Role;
import davideabbadessa.prontonoleggio_BE.enums.Sesso;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
//@JsonIgnoreProperties({"password"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String cognome;
    private int eta;
    @Enumerated(EnumType.STRING)
    private Sesso sesso;
    private String username;
    private String email;
    private String password;
    private String telefono;
    private String indirizzo;
    private int numeroCivico;
    private String citta;
    private String cap;
    private String provincia;
    private String nazione;
    private LocalDate dataNascita;
    private String codiceFiscale;
    private String patente;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Utente(String nome, String cognome, int eta, Sesso sesso, String username, String email, String password, String telefono, String indirizzo, int numeroCivico, String citta, String cap, String provincia, String nazione, LocalDate dataNascita, String codiceFiscale, String patente) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.sesso = sesso;
        this.username = username;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.citta = citta;
        this.cap = cap;
        this.provincia = provincia;
        this.nazione = nazione;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.patente = patente;
        this.role = Role.ROLE_USER;
    }


    public Utente(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.ROLE_USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
