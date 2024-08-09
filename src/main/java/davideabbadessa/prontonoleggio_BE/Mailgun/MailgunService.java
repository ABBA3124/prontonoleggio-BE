package davideabbadessa.prontonoleggio_BE.Mailgun;


import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
