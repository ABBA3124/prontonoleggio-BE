package davideabbadessa.prontonoleggio_BE.recensioni.entities;

import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "recensioni")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    private int rating;
    private String titolo;
    private String commento;
    private LocalDateTime dataCreazione;

}
