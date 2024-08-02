package davideabbadessa.prontonoleggio_BE.paypal.controllers;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import davideabbadessa.prontonoleggio_BE.paypal.services.PayPalService;
import davideabbadessa.prontonoleggio_BE.prenotazioni.entities.Prenotazione;
import davideabbadessa.prontonoleggio_BE.prenotazioni.payload.PrenotazioneDTO;
import davideabbadessa.prontonoleggio_BE.prenotazioni.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/paypal")
public class PayPalController {

    private static final Logger LOGGER = Logger.getLogger(PayPalController.class.getName());

    @Autowired
    private PayPalService paypalService;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    private PayPalHttpClient client;

    @Autowired
    public PayPalController(@Value("${paypal.client.id}") String clientId,
                            @Value("${paypal.client.secret}") String clientSecret) {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        this.client = new PayPalHttpClient(environment);
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody Map<String, Object> paymentDetails) {
        LOGGER.info("Initiating payment with details: " + paymentDetails);
        double total = Double.parseDouble(paymentDetails.get("total")
                                                        .toString());
        try {
            Payment payment = paypalService.createPayment(
                    total,
                    "EUR",
                    "paypal",
                    "sale",
                    "Payment description",
                    "http://localhost:12000/paypal/cancel",
                    "http://localhost:12000/paypal/success");
            for (com.paypal.api.payments.Links links : payment.getLinks()) {
                if (links.getRel()
                         .equals("approval_url")) {
                    LOGGER.info("Pagamento creato con successo, approval URL: " + links.getHref());
                    return ResponseEntity.ok(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            LOGGER.severe("Errore durante la creazione del pagamento: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Errore durante la creazione del pagamento.");
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId,
                                             @RequestParam("PayerID") String payerId,
                                             @RequestParam("veicoloId") String veicoloIdd,
                                             @RequestParam("utenteId") String utenteIdd,
                                             @RequestParam("dataInizio") String dataInizio,
                                             @RequestParam("dataFine") String dataFine) {
        OrdersGetRequest request = new OrdersGetRequest(paymentId);
        try {
            Order order = client.execute(request)
                                .result();
            LOGGER.info("Order status: " + order.status());
            if ("COMPLETED".equals(order.status())) {

                //Converto le date in LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(dataInizio, formatter);
                LocalDate endDate = LocalDate.parse(dataFine, formatter);

                //Converto le stringhe in UUID
                java.util.UUID veicoloId = java.util.UUID.fromString(veicoloIdd);
                java.util.UUID utenteId = java.util.UUID.fromString(utenteIdd);

                //Crea Prenotazione
                PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO(veicoloId, utenteId, startDate, endDate);
                Prenotazione prenotazione = prenotazioneService.creaPrenotazione(prenotazioneDTO);

                LOGGER.info("Prenotazione creata con successo: " + prenotazione.getId());
                return ResponseEntity.ok("Prenotazione creata con successo: " + prenotazione.getId());
            } else {
                LOGGER.warning("Payment not approved. Order status: " + order.status());
            }
        } catch (IOException e) {
            LOGGER.severe("IOException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Errore durante l'esecuzione del pagamento: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Errore durante la creazione della prenotazione: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Errore durante l'esecuzione del pagamento.");
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        LOGGER.info("Payment canceled");
        return "cancel";
    }
}
