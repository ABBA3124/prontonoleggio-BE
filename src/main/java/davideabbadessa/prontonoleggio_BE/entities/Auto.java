package davideabbadessa.prontonoleggio_BE.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "auto")
public class Auto extends Veicolo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String motorizzazione;
    private String trasmissione;
    private String trazione;
    private int porte;
    private int capacitaBagagliaio;
    private int airbag;
    private boolean abs;
    private boolean controlloStabilita;
    private boolean ariaCondizionata;
    private boolean sistemaNavigazione;
    private String sistemaAudio;
    private boolean bluetooth;
    private boolean sediliRiscaldati;

    public Auto(String motorizzazione, String trasmissione, String trazione, int porte, int capacitaBagagliaio, int airbag, boolean abs, boolean controlloStabilita, boolean ariaCondizionata, boolean sistemaNavigazione, String sistemaAudio, boolean bluetooth, boolean sediliRiscaldati) {
        this.motorizzazione = motorizzazione;
        this.trasmissione = trasmissione;
        this.trazione = trazione;
        this.porte = porte;
        this.capacitaBagagliaio = capacitaBagagliaio;
        this.airbag = airbag;
        this.abs = abs;
        this.controlloStabilita = controlloStabilita;
        this.ariaCondizionata = ariaCondizionata;
        this.sistemaNavigazione = sistemaNavigazione;
        this.sistemaAudio = sistemaAudio;
        this.bluetooth = bluetooth;
        this.sediliRiscaldati = sediliRiscaldati;
    }
}
