package davideabbadessa.prontonoleggio_BE.prenotazioni.entities;

import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Veicolo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "veicolo_id", nullable = false)
    private Veicolo veicolo;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    private LocalDateTime dataCreazione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}
