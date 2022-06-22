package com.projetb3.api.controller;

import com.projetb3.api.model.DebitCard;
import com.projetb3.api.service.DebitCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/cartes-de-paiement")
public class DebitCardController {

    private DebitCardService debitCardService;

    public DebitCardController(DebitCardService debitCardService) {
        this.debitCardService = debitCardService;
    }

    @GetMapping
    public ResponseEntity<Iterable<DebitCard>> getAll(@RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<DebitCard> debitCardsList = debitCardService.getAll();
            return ResponseEntity.ok(debitCardsList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebitCard> get(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Optional<DebitCard> debitCard = debitCardService.get(id);
            if (debitCard.isPresent()) {
                return ResponseEntity.ok(debitCard.get());
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/utilisateur={id}")
    public ResponseEntity<Iterable<DebitCard>> getCardOfUser(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Iterable<DebitCard> debitCardsList = debitCardService.getCardOfUser(id);
        Optional<Integer> idOptional = Optional.of(debitCardsList.iterator().next().getUser().getId());
        if (verifySenderOfRequest(token, idOptional)) {
            return ResponseEntity.ok(debitCardsList);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DebitCard debitCard, @RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.of(debitCard.getUser().getId()))) {
            debitCardService.save(debitCard);
            return ResponseEntity.ok().body("La carte de paiement a été créée.");
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<DebitCard> debitCard = debitCardService.get(id);
        if (debitCard.isPresent() && verifySenderOfRequest(token, Optional.of(debitCard.get().getUser().getId()))) {
            debitCardService.delete(id);
            return ResponseEntity.ok().body("La carte de paiement a été supprimée.");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id,
                                         @RequestBody DebitCard modified,
                                         @RequestHeader("Authentication") final String token) {
        Optional<DebitCard> optDebitCard = debitCardService.get(id);
        if (optDebitCard.isPresent() && verifySenderOfRequest(token, Optional.of(optDebitCard.get().getUser().getId()))) {
                DebitCard current = optDebitCard.get();
                if (modified.getNumber() != null) {
                    current.setNumber(modified.getNumber());
                }
                if (modified.getCvc() != null) {
                    current.setCvc(modified.getCvc());
                }
                if (modified.getExpiryYear() != null) {
                    current.setExpiryYear(modified.getExpiryYear());
                }
                if (modified.getExpiryMonth() != null) {
                    current.setExpiryMonth(modified.getExpiryMonth());
                }
                debitCardService.save(current);
                return ResponseEntity.ok().body("La carte de paiement " + current.getId() + " a été modifiée.");
            }
        return ResponseEntity.badRequest().build();
    }
}
