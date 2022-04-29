package com.projetb3.api.controller;

import com.projetb3.api.model.Article;
import com.projetb3.api.model.Commande;
import com.projetb3.api.service.CommandeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    private CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Commande>> getAllCommandes() {
        Iterable<Commande> listeCommandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(listeCommandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommande(@PathVariable("id") final int id) {
        Optional<Commande> commande = commandeService.getCommande(id);
        if (commande.isPresent()) {
            return ResponseEntity.ok(commande.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createCommande(@RequestBody Commande commande) {
        commande.setDate_achat(Commande.now());
        int total = getPrixTot(commande.getArticles());
        commande.setPrix_tot(total);
        if(commande.getArticles().isEmpty() || commande.getUtilisateur().getId() != 0){
            return ResponseEntity.badRequest().body("Veuillez entrer une requete valide.");
        }
        commandeService.saveCommande(commande);
        return ResponseEntity.ok().body("La commande a été créée.");
    }

    private int getPrixTot(List<Article> articles) {
        List<Integer> listePrix = new ArrayList<>();
        for (Article article : articles) {
            listePrix.add(article.getPrix());
        }
        Optional<Integer> prixTot = listePrix.stream().reduce(Integer::sum);
        return prixTot.orElse(0);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable("id") final int id) {
        Optional<Commande> optCommande = commandeService.getCommande(id);
        if (optCommande.isPresent()){
            commandeService.deleteCommande(id);
            return ResponseEntity.ok().body("La commande a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCommande(@PathVariable("id") final int id, @RequestBody Commande modification) {
        Optional<Commande> optCommande = commandeService.getCommande(id);
        if (optCommande.isPresent()) {
            Commande current = optCommande.get();
            if (modification.getNumero() != null) {
                current.setNumero(modification.getNumero());
            }
            if (modification.getPrix_tot() != 0) {
                current.setPrix_tot(modification.getPrix_tot());
            }
            commandeService.saveCommande(current);
            return ResponseEntity.ok().body("La commande " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }
}
