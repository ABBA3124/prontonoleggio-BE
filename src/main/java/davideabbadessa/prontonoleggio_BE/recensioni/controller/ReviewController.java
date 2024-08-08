package davideabbadessa.prontonoleggio_BE.recensioni.controller;

import davideabbadessa.prontonoleggio_BE.recensioni.entities.Review;
import davideabbadessa.prontonoleggio_BE.recensioni.payload.ReviewDTO;
import davideabbadessa.prontonoleggio_BE.recensioni.service.ReviewService;
import davideabbadessa.prontonoleggio_BE.utente.entities.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recensioni")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    // <------------------ SUPER ADMIN ------------------>
    // GET /recensioni/user/{utenteId}
    @GetMapping("/user/{utenteId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable UUID utenteId) {
        List<Review> reviews = reviewService.getRecensioniByUtente(utenteId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // GET /recensioni/{recensioneId}
    @GetMapping("/{recensioneId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Review> getReviewById(@PathVariable UUID recensioneId) {
        Review review = reviewService.getRecensioneById(recensioneId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // <------------------ USER ------------------>
    // POST /recensioni/create
    @PostMapping("/crea")
    public ResponseEntity<Review> createReview(@Validated @RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.creaRecensione(reviewDTO);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    // GET /recensioni/all
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllRecensioni();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // GET /recensioni/me
    @GetMapping("/me")
    public ResponseEntity<List<Review>> getMyReviews(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        List<Review> reviews = reviewService.getRecensioniByUtente(currentAuthenticatedUtente.getId());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // PUT /recensioni/update/{recensioneId}
    @PutMapping("/update/{recensioneId}")
    public ResponseEntity<Review> updateReview(@PathVariable UUID recensioneId, @Validated @RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.updateRecensione(recensioneId, reviewDTO);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
}
