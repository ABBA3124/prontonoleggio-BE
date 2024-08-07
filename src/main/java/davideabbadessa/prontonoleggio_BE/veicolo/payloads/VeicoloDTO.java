package davideabbadessa.prontonoleggio_BE.veicolo.payloads;

import davideabbadessa.prontonoleggio_BE.veicolo.enums.Disponibilita;
import davideabbadessa.prontonoleggio_BE.veicolo.enums.TipoVeicolo;
import jakarta.validation.constraints.*;

// DTO per la creazione di un nuovo veicolo
public record VeicoloDTO(

        // <---------- Set Veicolo important ---------->
        @NotNull(message = "Tipo veicolo è obbligatorio")
        TipoVeicolo tipoVeicolo,

        @NotNull(message = "Disponibilità è obbligatoria")
        Disponibilita disponibilita,

        // <---------- Sede Veicolo ---------->
        @NotBlank(message = "Nome Sede è obbligatoria")
        String nomeSede,

        @NotBlank(message = "Citta Sede è obbligatoria")
        String cittaSede,

        @NotBlank(message = "Via Sede è obbligatoria")
        String viaSede,

        @NotBlank(message = "Provincia Sede è obbligatoria")
        String provinciaSede,

        @NotBlank(message = "Telefono Sede è obbligatoria")
        String telefonoSede,

        @NotBlank(message = "Email Sede è obbligatoria")
        String emailSede,

        @NotBlank(message = "Orari Sede è obbligatoria")
        String orariSede,


        // <---------- Dati Veicolo ---------->
        @NotBlank(message = "Targa è obbligatoria")
        String targa,

        @NotBlank(message = "Immagine è obbligatoria")
        String immagini,

        @NotBlank(message = "Marca è obbligatoria")
        String marca,

        @NotBlank(message = "Modello è obbligatorio")
        String modello,

        @Min(value = 1886, message = "Anno deve essere maggiore di 1885")
        @Max(value = 2024, message = "Anno non può essere maggiore di 2024")
        int anno,

        @NotBlank(message = "Categoria è obbligatoria")
        String categoria,

        @NotBlank(message = "Alimentazione è obbligatoria")
        String alimentazione,

        @NotBlank(message = "Cambio è obbligatorio")
        String cambio,

        @NotBlank(message = "Trazione è obbligatoria")
        String trazione,

        @Positive(message = "Cilindrata deve essere positiva")
        int cilindrata,

        @Positive(message = "Potenza deve essere positiva")
        int potenzaKw,

        @Positive(message = "Consumo carburante deve essere positivo")
        double consumoCarburante,

        @Positive(message = "Posti deve essere positivo")
        int posti,

        @Positive(message = "Tariffa giornaliera deve essere positiva")
        double tariffaGiornaliera,


        @PositiveOrZero(message = "Chilometraggio deve essere zero o positivo")
        int chilometraggio,

        @NotBlank(message = "Documenti assicurativi è obbligatorio")
        String documentiAssicurativi,

        @NotBlank(message = "Revisione è obbligatoria")
        String revisione,

        boolean abs,


        //<---------- Proprietà specifiche per Auto ---------->
        @PositiveOrZero(message = "Porte deve essere zero o positivo")
        int porte,

        @PositiveOrZero(message = "Capacità bagagliaio deve essere zero o positivo")
        int capacitaBagagliaio,

        @PositiveOrZero(message = "Airbag deve essere zero o positivo")
        int airbag,

        boolean controlloStabilita,

        boolean ariaCondizionata,

        boolean sistemaNavigazione,

        String sistemaAudio,

        boolean bluetooth,

        boolean sediliRiscaldati,

        // <---------- Proprietà specifiche per Moto ---------->
        boolean bauletto,

        boolean parabrezza
) {
}
