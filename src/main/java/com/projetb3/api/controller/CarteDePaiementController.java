package com.projetb3.api.controller;

import com.projetb3.api.model.CarteDePaiement;
import com.projetb3.api.service.CarteDePaiementService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cartes-de-paiement")
public class CarteDePaiementController {

    private CarteDePaiementService carteDePaiementService;

    public CarteDePaiementController(CarteDePaiementService carteDePaiementService) {
        this.carteDePaiementService = carteDePaiementService;
    }

    @GetMapping
    public ResponseEntity<Page<CarteDePaiement>> getAllCarteDePaiementsToWatch(@RequestParam("page") final Optional<Integer> page,
                                                                               @RequestParam("sortBy") final Optional<String> sortBy,
                                                                               @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<CarteDePaiement> listeCartes = carteDePaiementService.getAllCartesDePaiement(page, sortBy, orderBy);
        return ResponseEntity.ok(listeCartes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarteDePaiement> getCarteDePaiement(@PathVariable("id") final int id) {
        Optional<CarteDePaiement> carteDePaiement = carteDePaiementService.getCarteDePaiement(id);
        if (carteDePaiement.isPresent()) {
            return ResponseEntity.ok(carteDePaiement.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createCarteDePaiement(@RequestBody CarteDePaiement carteDePaiement) {
        carteDePaiementService.saveCarteDePaiement(carteDePaiement);
        return ResponseEntity.ok().body("La carte de paiement a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarteDePaiement(@PathVariable("id") final int id) {
        Optional<CarteDePaiement> optCarteDePaiement = carteDePaiementService.getCarteDePaiement(id);

        if (optCarteDePaiement.isPresent()) {
            carteDePaiementService.deleteCarteDePaiement(id);
            return ResponseEntity.ok().body("La carte de paiement a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarteDePaiement(@PathVariable("id") final int id, @RequestBody CarteDePaiement modification) {
        Optional<CarteDePaiement> optCarteDePaiement = carteDePaiementService.getCarteDePaiement(id);
        if (optCarteDePaiement.isPresent()) {
            CarteDePaiement current = optCarteDePaiement.get();
            if (modification.getNumero() != null) {
                current.setNumero(modification.getNumero());
            }
            if (modification.getCvc() != null) {
                current.setCvc(modification.getCvc());
            }
            if (modification.getAnnee_expiration() != null) {
                current.setAnnee_expiration(modification.getAnnee_expiration());
            }
            if (modification.getMois_expiration() != null) {
                current.setMois_expiration(modification.getMois_expiration());
            }
            carteDePaiementService.saveCarteDePaiement(current);
            return ResponseEntity.ok().body("La carte de paiement " + current.getId()+ " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

}
