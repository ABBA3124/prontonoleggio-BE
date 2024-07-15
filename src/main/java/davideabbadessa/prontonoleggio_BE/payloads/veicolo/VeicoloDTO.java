package davideabbadessa.prontonoleggio_BE.payloads.veicolo;

import davideabbadessa.prontonoleggio_BE.enums.veicolo.Disponibilita;
import davideabbadessa.prontonoleggio_BE.enums.veicolo.TipoVeicolo;
import jakarta.validation.constraints.*;


public record VeicoloDTO(
        @NotBlank(message = "Marca è obbligatoria")
        String marca,

        @NotBlank(message = "Modello è obbligatorio")
        String modello,

        @Min(value = 1886, message = "Anno deve essere maggiore di 1885")
        @Max(value = 2024, message = "Anno non valido")
        int anno,

        @NotBlank(message = "Targa è obbligatoria")
        String targa,

        @NotNull(message = "Tipo veicolo è obbligatorio")
        TipoVeicolo tipoVeicolo,

        @NotBlank(message = "Categoria è obbligatoria")
        String categoria,

        @Positive(message = "Cilindrata deve essere positiva")
        int cilindrata,

        @Positive(message = "Potenza deve essere positiva")
        int potenza,

        @Positive(message = "Consumo carburante deve essere positivo")
        double consumoCarburante,

        @Positive(message = "Posti deve essere positivo")
        int posti,

        @Positive(message = "Tariffa giornaliera deve essere positiva")
        double tariffaGiornaliera,

        @NotNull(message = "Disponibilità è obbligatoria")
        Disponibilita disponibilita,

        @PositiveOrZero(message = "Chilometraggio deve essere zero o positivo")
        int chilometraggio,

        @NotBlank(message = "Posizione è obbligatoria")
        String posizione,

        String documentiAssicurativi,

        String revisione,

        String immagini,

        // Proprietà specifiche per Auto
        String motorizzazione,
        String trasmissione,
        String trazione,
        @PositiveOrZero(message = "Porte deve essere zero o positivo")
        int porte,
        @PositiveOrZero(message = "Capacità bagagliaio deve essere zero o positivo")
        int capacitaBagagliaio,
        @PositiveOrZero(message = "Airbag deve essere zero o positivo")
        int airbag,
        boolean abs,
        boolean controlloStabilita,
        boolean ariaCondizionata,
        boolean sistemaNavigazione,
        String sistemaAudio,
        boolean bluetooth,
        boolean sediliRiscaldati,

        // Proprietà specifiche per Moto
        boolean bauletto,
        boolean parabrezza,
        boolean controlloTrattamento
) {
}
