package com.projetb3.api_watashihouse.controller;

import com.projetb3.api_watashihouse.model.Article;
import com.projetb3.api_watashihouse.model.Commande;
import com.projetb3.api_watashihouse.service.CommandeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    private CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public ResponseEntity<Page<Commande>> getAllCommandesToWatch(
            @RequestParam("page") final Optional<Integer> page,
            @RequestParam("sortBy") final Optional<String> sortBy
    ) {
        Page<Commande> commandeList = commandeService.getAllCommandes(page, sortBy);
        return ResponseEntity.ok(commandeList);
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
        if(commande.getArticles().isEmpty() || commande.getUtilisateur().getId() == null){
            return ResponseEntity.badRequest().body("Veuillez entrer une requete valide.");
        }
        commandeService.saveCommande(commande);
        return ResponseEntity.ok().body("Creation de la commande reussie !");
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
    public ResponseEntity<Void> deleteCommande(@PathVariable("id") final int id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        Optional<Commande> optCommande = commandeService.getCommande(id);  //Optional -> encapsule un objet dont la valeur peut être null
        if (optCommande.isPresent()){
            commandeService.deleteCommande(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCommande(@PathVariable("id") final int id, @RequestBody Commande commande) {
        Optional<Commande> optCommande = commandeService.getCommande(id);
        if (optCommande.isPresent()) {
            Commande currentCommande = optCommande.get();
            String numero = commande.getNumero();
            String date = commande.getDate_achat();
            int prix_tot = commande.getPrix_tot();

            if (numero != null) {
                currentCommande.setNumero(numero);
            }
            if (prix_tot != 0) {
                currentCommande.setPrix_tot(prix_tot);
            }
            if (date != null) {
                currentCommande.setDate_achat(date);
            }
            commandeService.saveCommande(currentCommande);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
