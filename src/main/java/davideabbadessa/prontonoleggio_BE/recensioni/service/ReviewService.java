package davideabbadessa.prontonoleggio_BE.recensioni.service;

import davideabbadessa.prontonoleggio_BE.exceptions.NotFoundException;
import davideabbadessa.prontonoleggio_BE.recensioni.entities.Review;
import davideabbadessa.prontonoleggio_BE.recensioni.payload.ReviewDTO;
import davideabbadessa.prontonoleggio_BE.recensioni.repositorie.ReviewRepository;
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

    public Review creaRecensione(ReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        Utente utente = (Utente) authentication.getPrincipal();

        List<Review> existingReviews = reviewRepository.findAllByUtenteId(utente.getId());
        if (!existingReviews.isEmpty()) {
            throw new NotFoundException("Hai già creato una recensione.");
        }

        Review review = new Review();
        review.setUtente(utente);
        review.setRating(reviewDTO.rating());
        review.setTitolo(reviewDTO.titolo());
        review.setCommento(reviewDTO.commento());
        review.setDataCreazione(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getRecensioniByUtente(UUID utenteId) {
        return reviewRepository.findAllByUtenteId(utenteId);
    }

    public Review getRecensioneById(UUID recensioneId) {
        return reviewRepository.findById(recensioneId)
                               .orElseThrow(() -> new RuntimeException("Recensione non trovata"));
    }

    public List<Review> getAllRecensioni() {
        return reviewRepository.findAll();
    }

    public Review updateRecensione(UUID recensioneId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(recensioneId)
                                        .orElseThrow(() -> new RuntimeException("Recensione non trovata"));
        review.setRating(reviewDTO.rating());
        review.setTitolo(reviewDTO.titolo());
        review.setCommento(reviewDTO.commento());
        review.setDataCreazione(LocalDateTime.now());
        return reviewRepository.save(review);
    }

}
