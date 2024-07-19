package davideabbadessa.prontonoleggio_BE.payloads.utente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ModificaUtenteDTO(

        @NotBlank(message = "Il nome non può essere vuoto!")
        @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Il nome non può contenere numeri o caratteri speciali!")
        String nome,

        @NotBlank(message = "Il cognome non può essere vuoto!")
        @Size(min = 2, max = 50, message = "Il cognome deve essere tra 2 e 50 caratteri")
        @Pattern(regexp = "^[a-zA-Z]*$", message = "Il cognome non può contenere numeri o caratteri speciali!")
        String cognome,

        @NotBlank(message = "L'username non può essere vuoto!")
        @Size(min = 4, max = 20, message = "L'username deve essere tra 4 e 20 caratteri")
        String username,

        @NotBlank(message = "Email non può essere vuota!")
        @Email(message = "Email non valida!")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email non valida!")
        String email,

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
        String nazione

) {
}

