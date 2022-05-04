package com.projetb3.api.controller;

import com.projetb3.api.model.DebitCard;
import com.projetb3.api.service.DebitCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cartes-de-paiement")
public class DebitCardController {

    private DebitCardService debitCardService;

    public DebitCardController(DebitCardService debitCardService) {
        this.debitCardService = debitCardService;
    }

    @GetMapping
    public ResponseEntity<Iterable<DebitCard>> getAll() {
        Iterable<DebitCard> listeCartes = debitCardService.getAll();
        return ResponseEntity.ok(listeCartes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebitCard> get(@PathVariable("id") final int id) {
        Optional<DebitCard> debitCard = debitCardService.get(id);
        if (debitCard.isPresent()) {
            return ResponseEntity.ok(debitCard.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DebitCard debitCard) {
        debitCardService.save(debitCard);
        return ResponseEntity.ok().body("La carte de paiement a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<DebitCard> optDebitCard = debitCardService.get(id);

        if (optDebitCard.isPresent()) {
            debitCardService.delete(id);
            return ResponseEntity.ok().body("La carte de paiement a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody DebitCard modified) {
        Optional<DebitCard> optDebitCard = debitCardService.get(id);
        if (optDebitCard.isPresent()) {
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
            return ResponseEntity.ok().body("La carte de paiement " + current.getId()+ " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

}
