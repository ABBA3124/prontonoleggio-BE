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

    private String marca;
    private String modello;
    private int anno;
    private String targa;
    @Enumerated(EnumType.STRING)
    private TipoVeicolo tipoVeicolo;
    private String categoria;
    private int cilindrata;
    private int potenza;
    private double consumoCarburante;
    private int posti;
    private double tariffaGiornaliera;
    @Enumerated(EnumType.STRING)
    private Disponibilita disponibilita;
    private int chilometraggio;
    private String posizione;
    private String viaSede;
    private String documentiAssicurativi;
    private String revisione;
    private String immagini;
    private LocalDateTime dataCreazioneVeicolo;
}
