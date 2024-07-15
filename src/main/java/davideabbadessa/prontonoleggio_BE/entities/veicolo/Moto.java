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
@Table(name = "moto")
public class Moto extends Veicolo {

    private boolean bauletto;
    private boolean parabrezza;
    private boolean abs;
    private boolean controlloTrattamento;


}
