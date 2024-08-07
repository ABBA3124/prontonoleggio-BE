package davideabbadessa.prontonoleggio_BE.veicolo.entities;

import davideabbadessa.prontonoleggio_BE.veicolo.enums.Disponibilita;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.TipoVeicolo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Veicolo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // <---------- Set Veicolo important ---------->
    private LocalDateTime dataCreazioneVeicolo;
    @Enumerated(EnumType.STRING)
    private TipoVeicolo tipoVeicolo;
    @Enumerated(EnumType.STRING)
    private Disponibilita disponibilita;

    // <---------- Sede Veicolo ---------->
    private String nomeSede;
    private String cittaSede;
    private String viaSede;
    private String provinciaSede;
    private String telefonoSede;
    private String emailSede;
    private String orariSede;

    // <---------- Dati Veicolo ---------->
    private String targa;
    private String immagini;
    private String marca;
    private String modello;
    private int anno;
    private String categoria;
    private String alimentazione;
    private String cambio;
    private String trazione;
    private int cilindrata;
    private int potenzaKw;
    private double consumoCarburante;
    private int posti;
    private double tariffaGiornaliera;
    private int chilometraggio;
    private String documentiAssicurativi;
    private String revisione;
    private boolean abs;

}
