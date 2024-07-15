package davideabbadessa.prontonoleggio_BE.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Veicolo {

    private String marca;
    private String modello;
    private int anno;
    private String targa;
    private String categoria;
    private int cilindrata;
    private int potenza;
    private double consumoCarburante;
    private int posti;
    private double tariffaGiornaliera;
    private String disponibilita;
    private int chilometraggio;
    private String posizione;
    private String documentiAssicurativi;
    private String revisione;
    private String immagini;
}
