package davideabbadessa.prontonoleggio_BE.recensioni.service;

import davideabbadessa.prontonoleggio_BE.Mailgun.MailgunService;
import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.prenotazioni.entities.Prenotazione;
import davideabbadessa.prontonoleggio_BE.prenotazioni.repositories.PrenotazioneRepository;
import davideabbadessa.prontonoleggio_BE.recensioni.entities.Review;
import davideabbadessa.prontonoleggio_BE.recensioni.payload.ReviewDTO;
import davideabbadessa.prontonoleggio_BE.recensioni.repositories.ReviewRepository;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import davideabbadessa.prontonoleggio_BE.utente.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private MailgunService mailgunService;

    public Review creaRecensione(ReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        Utente utente = (Utente) authentication.getPrincipal();

        Prenotazione prenotazione = prenotazioneRepository.findById(reviewDTO.prenotazioneId())
                                                          .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        // Verifica se esiste già una recensione per questa prenotazione
        if (reviewRepository.findByPrenotazioneId(prenotazione.getId())
                            .isPresent()) {
            throw new NotFoundException("Esiste già una recensione per questa prenotazione.");
        }

        Review review = new Review();
        review.setUtente(utente);
        review.setPrenotazione(prenotazione);
        review.setRating(reviewDTO.rating());
        review.setTitolo(reviewDTO.titolo());
        review.setCommento(reviewDTO.commento());
        review.setDataCreazione(LocalDateTime.now());

        // Salvataggio della recensione nel database
        Review reviewSalvata = reviewRepository.save(review);

        // Invio dell'email di conferma
        mailgunService.sendReviewConfermaEmail(utente, reviewSalvata);

        return reviewSalvata;
    }

    public List<Review> getRecensioniByUtente(UUID utenteId) {
        return reviewRepository.findAllByUtenteIdOrderByDataCreazioneDesc(utenteId);
    }

    public Review getRecensioneById(UUID recensioneId) {
        return reviewRepository.findById(recensioneId)
                               .orElseThrow(() -> new RuntimeException("Recensione non trovata"));
    }

    public List<Review> getAllRecensioni() {
        return reviewRepository.findAllByOrderByDataCreazioneDesc();
    }

    public Review updateRecensione(UUID recensioneId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(recensioneId)
                                        .orElseThrow(() -> new RuntimeException("Recensione non trovata"));
        Prenotazione prenotazione = prenotazioneRepository.findById(reviewDTO.prenotazioneId())
                                                          .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        review.setRating(reviewDTO.rating());
        review.setTitolo(reviewDTO.titolo());
        review.setCommento(reviewDTO.commento());
        review.setPrenotazione(prenotazione);
        review.setDataModifica(LocalDateTime.now());
        return reviewRepository.save(review);
    }

}
