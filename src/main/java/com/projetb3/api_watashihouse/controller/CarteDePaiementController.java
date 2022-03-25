package com.projetb3.api_watashihouse.controller;

import com.projetb3.api_watashihouse.model.CarteDePaiement;
import com.projetb3.api_watashihouse.service.CarteDePaiementService;
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
    public ResponseEntity<Page<CarteDePaiement>> getAllCarteDePaiementsToWatch(@RequestParam("page") final Optional<Integer> page, @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<CarteDePaiement> carteDePaiementList = carteDePaiementService.getAllCartesDePaiement(page, sortBy);
        return ResponseEntity.ok(carteDePaiementList);
    }

    /*
        @RequestParam recupere des infos concernant les ressources, tout ce qu'on peut trouver apres le ?, ses infos servent principalement de filtrage
        @PathVariable récupère la ressource directement soit les champs contenu dans notre bdd (id, title, date_released)
     */

    @GetMapping("/{id}")
    public ResponseEntity<CarteDePaiement> getCarteDePaiement(@PathVariable("id") final int id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<CarteDePaiement> carteDePaiement = carteDePaiementService.getCarteDePaiement(id); //Optional -> encapsule un objet dont la valeur peut être null
        if (carteDePaiement.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(carteDePaiement.get());  //recupere la valeur de carteDePaiement
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCarteDePaiement(@RequestBody CarteDePaiement carteDePaiement) {         // deserialise les JSON dans un langage Java -> regroupe des données séparées dans un meme flux
        // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de CarteDePaiement
        carteDePaiementService.saveCarteDePaiement(carteDePaiement);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarteDePaiement(@PathVariable("id") final int id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        Optional<CarteDePaiement> optCarteDePaiement = carteDePaiementService.getCarteDePaiement(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optCarteDePaiement.isPresent()){
            carteDePaiementService.deleteCarteDePaiement(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCarteDePaiement(@PathVariable("id") final int id, @RequestBody CarteDePaiement carteDePaiement) { //carteDePaiement contenu dans le body
        Optional<CarteDePaiement> optCarteDePaiement = carteDePaiementService.getCarteDePaiement(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optCarteDePaiement.isPresent()) {
            CarteDePaiement currentCarteDePaiement = optCarteDePaiement.get();

            //recupere les variables du carteDePaiement fourni en parametre pour les manipuler
            String numero = carteDePaiement.getNumero();
            String cvc = carteDePaiement.getCvc();
            String annee_expiration = carteDePaiement.getAnnee_expiration();
            String mois_expiration = carteDePaiement.getMois_expiration();

            if (numero != null) {
                currentCarteDePaiement.setNumero(numero);
            }
            if (cvc != null) {
                currentCarteDePaiement.setCvc(cvc);
            }
            if (annee_expiration != null) {
                currentCarteDePaiement.setAnnee_expiration(annee_expiration);
            }
            if (mois_expiration != null) {
                currentCarteDePaiement.setMois_expiration(mois_expiration);
            }
            carteDePaiementService.saveCarteDePaiement(currentCarteDePaiement);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
