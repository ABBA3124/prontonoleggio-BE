package davideabbadessa.prontonoleggio_BE.entities;

import davideabbadessa.prontonoleggio_BE.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
//@JsonIgnoreProperties
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String telefono;
    private String indirizzo;
    private String citta;
    private String cap;
    private String provincia;
    private String nazione;
    private String dataNascita;
    private String codiceFiscale;


}
