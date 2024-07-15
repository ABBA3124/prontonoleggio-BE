package davideabbadessa.prontonoleggio_BE.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "moto")
public class Moto extends Veicolo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private boolean bauletto;
    private boolean parabrezza;
    private boolean abs;
    private boolean controlloTrattamento;

    public Moto(boolean bauletto, boolean parabrezza, boolean abs, boolean controlloTrattamento) {
        this.bauletto = bauletto;
        this.parabrezza = parabrezza;
        this.abs = abs;
        this.controlloTrattamento = controlloTrattamento;
    }
}
