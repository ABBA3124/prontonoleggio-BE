package davideabbadessa.prontonoleggio_BE.utente.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import davideabbadessa.prontonoleggio_BE.utente.enums.Role;
import davideabbadessa.prontonoleggio_BE.utente.enums.Sesso;
import davideabbadessa.prontonoleggio_BE.utente.payloads.NuovoUtenteDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"password", "enabled", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
@Table(name = "utenti")
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
    private String numeroCivico;
    private String citta;
    private String cap;
    private String provincia;
    private String nazione;
    private LocalDate dataNascita;
    private String codiceFiscale;
    private String patente;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String avatar;

    public Utente(NuovoUtenteDTO dto) {
        this.nome = dto.nome();
        this.cognome = dto.cognome();
        this.sesso = dto.sesso();
        this.username = dto.username();
        this.email = dto.email();
        this.password = dto.password();
        this.telefono = dto.telefono();
        this.indirizzo = dto.indirizzo();
        this.numeroCivico = dto.numeroCivico();
        this.citta = dto.citta();
        this.cap = dto.cap();
        this.provincia = dto.provincia();
        this.nazione = dto.nazione();
        this.dataNascita = dto.dataNascita();
        this.codiceFiscale = dto.codiceFiscale();
        this.patente = dto.patente();
        this.role = Role.ROLE_USER;
        this.eta = Period.between(this.dataNascita, LocalDate.now())
                         .getYears();
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
