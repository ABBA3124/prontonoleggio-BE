package davideabbadessa.prontonoleggio_BE.Mailgun;


import davideabbadessa.prontonoleggio_BE.prenotazioni.entities.Prenotazione;
import davideabbadessa.prontonoleggio_BE.recensioni.entities.Review;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.veicolo.entities.Veicolo;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class MailgunService {

    @Value("${mailgun.apikey}")
    private String apiKey;

    @Value("${mailgun.domainname}")
    private String domainName;

    public void sendEmail(String to, String subject, String text) {
        HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                                              .basicAuth("api", apiKey)
                                              .queryString("from", "no-reply@" + domainName)
                                              .queryString("to", to)
                                              .queryString("subject", subject)
                                              .queryString("text", text)
                                              .asString();

        if (request.getStatus() != 200) {
            throw new RuntimeException("Failed to send email: " + request.getBody());
        }
    }

    public void sendPrenotazioneConfermaEmail(Utente utente, Prenotazione prenotazione) {
        String subject = "Conferma Prenotazione";

        // Formattazione delle date (solo data, senza ora)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String dataInizioFormattata = prenotazione.getDataInizio()
                                                  .format(formatter);
        String dataFineFormattata = prenotazione.getDataFine()
                                                .format(formatter);

        // Estrazione delle informazioni del veicolo
        Veicolo veicolo = prenotazione.getVeicolo();
        String veicoloInfo = veicolo.getMarca() + " " + veicolo.getModello();
        String sedeInfo = veicolo.getNomeSede() + " " + veicolo.getViaSede();
        String orariSede = veicolo.getOrariSede();
        String numeroTelefonoSede = veicolo.getTelefonoSede();

        // Costruzione del testo dell'email
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Ciao ")
                   .append(utente.getNome())
                   .append(",\n\n")
                   .append("La tua prenotazione è stata confermata. Ecco i dettagli:\n\n")
                   .append("Veicolo: ")
                   .append(veicoloInfo)
                   .append("\n")
                   .append("Prenotazione Valida ")
                   .append("  ")
                   .append("DAL ")
                   .append(dataInizioFormattata)
                   .append(" - ")
                   .append("AL ")
                   .append(dataFineFormattata)
                   .append("\n\n")
                   .append("Vieni a ritirarla presso la sede: ")
                   .append(sedeInfo)
                   .append("\n")
                   .append("Recapito Telefonico: ")
                   .append(numeroTelefonoSede)
                   .append("\n\n")
                   .append("Orari apertura: ")
                   .append(orariSede)
                   .append("\n\n")
                   .append("Grazie per aver scelto il nostro servizio!\n\n")
                   .append("Cordiali saluti,\n")
                   .append("Il team di Prontonoleggio");

        sendEmail(utente.getEmail(), subject, textBuilder.toString());
    }

    public void sendReviewConfermaEmail(Utente utente, Review review) {
        String subject = "Conferma Creazione Recensione";

        // Estrazione delle informazioni della recensione
        Prenotazione prenotazione = review.getPrenotazione();
        Veicolo veicolo = prenotazione.getVeicolo();
        String veicoloInfo = veicolo.getMarca() + " " + veicolo.getModello();

        // Costruzione del testo dell'email
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Ciao ")
                   .append(utente.getNome())
                   .append(",\n\n")
                   .append("La tua recensione è stata creata con successo. Ecco i dettagli:\n\n")
                   .append("Veicolo: ")
                   .append(veicoloInfo)
                   .append("\n")
                   .append("Titolo: ")
                   .append(review.getTitolo())
                   .append("\n")
                   .append("Valutazione: ")
                   .append(review.getRating())
                   .append("\n")
                   .append("Commento: ")
                   .append(review.getCommento())
                   .append("\n\n")
                   .append("Grazie per aver condiviso la tua esperienza con noi!\n\n")
                   .append("Cordiali saluti,\n")
                   .append("Il team di Prontonoleggio");

        sendEmail(utente.getEmail(), subject, textBuilder.toString());
    }

}
