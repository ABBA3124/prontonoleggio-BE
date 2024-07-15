package davideabbadessa.prontonoleggio_BE.entities.veicolo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auto")
public class Auto extends Veicolo {

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


}
