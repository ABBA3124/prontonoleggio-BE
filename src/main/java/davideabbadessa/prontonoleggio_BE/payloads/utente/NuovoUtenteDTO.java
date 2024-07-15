package davideabbadessa.prontonoleggio_BE.payloads.utente;

import com.fasterxml.jackson.annotation.JsonFormat;
import davideabbadessa.prontonoleggio_BE.enums.utente.Sesso;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record NuovoUtenteDTO(

        @NotBlank(message = "Il nome non può essere vuoto!")
        @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Il nome non può contenere numeri o caratteri speciali!")
        String nome,

        @NotBlank(message = "Il cognome non può essere vuoto!")
        @Size(min = 2, max = 50, message = "Il cognome deve essere tra 2 e 50 caratteri")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Il cognome non può contenere numeri o caratteri speciali!")
        String cognome,

        @NotNull(message = "Il sesso non può essere vuoto!")
        Sesso sesso,

        @NotBlank(message = "L'username non può essere vuoto!")
        @Size(min = 4, max = 20, message = "L'username deve essere tra 4 e 20 caratteri")
        String username,

        @NotBlank(message = "Email non può essere vuota!")
        @Email(message = "Email non valida!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email non valida!")
        String email,

        @NotBlank(message = "La password non può essere vuota!")
        @Size(min = 8, message = "La password deve essere lunga almeno 8 caratteri!")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "La password deve contenere almeno una lettera maiuscola, una minuscola e un numero!")
        String password,

        @NotBlank(message = "Il telefono non può essere vuoto!")
        @Size(min = 10, max = 10, message = "Il telefono deve avere 10 cifre")
        @Pattern(regexp = "^[0-9]*$", message = "Il telefono non può contenere lettere o caratteri speciali!")
        String telefono,

        @NotBlank(message = "L'indirizzo non può essere vuoto!")
        @Size(min = 5, max = 100, message = "L'indirizzo deve essere tra 5 e 100 caratteri")
        String indirizzo,

        @NotBlank(message = "Il numero civico non può essere vuoto!")
        @Size(max = 10, message = "Il numero civico deve avere al massimo 10 caratteri")
        @Pattern(regexp = "^[0-9A-Za-z\\s]*$", message = "Il numero civico non può contenere caratteri speciali!")
        String numeroCivico,

        @NotBlank(message = "La città non può essere vuota!")
        @Size(min = 2, max = 50, message = "La città deve essere tra 2 e 50 caratteri")
        @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "La città non può contenere numeri o caratteri speciali!")
        String citta,

        @NotBlank(message = "Il CAP non può essere vuoto!")
        @Size(min = 5, max = 5, message = "Il CAP deve avere 5 cifre")
        @Pattern(regexp = "^[0-9]*$", message = "Il CAP non può contenere lettere o caratteri speciali!")
        String cap,

        @NotBlank(message = "La provincia non può essere vuota!")
        @Size(min = 2, max = 2, message = "La provincia deve avere 2 caratteri")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "La provincia non può contenere numeri o caratteri speciali!")
        String provincia,

        @NotBlank(message = "La nazione non può essere vuota!")
        @Size(min = 2, max = 50, message = "La nazione deve essere tra 2 e 50 caratteri")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "La nazione non può contenere numeri o caratteri speciali!")
        String nazione,

        @NotNull(message = "La data di nascita non può essere vuota!")
        @Past(message = "La data di nascita deve essere nel passato!")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascita,

        @NotBlank(message = "Il codice fiscale non può essere vuoto!")
        @Pattern(regexp = "^[A-Z]{6}\\d{2}[A-EHLMPR-T][0-9LMNP-V]{2}[A-Z]\\d{3}[A-Z]$", message = "Codice fiscale non valido!")
        String codiceFiscale,

        @NotBlank(message = "La patente non può essere vuota!")
        @Pattern(regexp = "^[A-Z]{2}\\d{7}$", message = "Patente non valida!")
        String patente
) {
}
