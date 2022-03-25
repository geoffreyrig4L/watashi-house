package com.projetb3.api_watashihouse.controller;

import com.projetb3.api_watashihouse.model.Categorie;
import com.projetb3.api_watashihouse.service.CategorieService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategorieController {

    private CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<Page<Categorie>> getAllCategoriesToWatch(@RequestParam("page") final Optional<Integer> page, @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<Categorie> listeCategorie = categorieService.getAllCategories(page, sortBy);
        return ResponseEntity.ok(listeCategorie);
    }

    /*
        @RequestParam recupere des infos concernant les ressources, tout ce qu'on peut trouver apres le ?, ses infos servent principalement de filtrage
        @PathVariable récupère la ressource directement soit les champs contenu dans notre bdd (id, title, date_released)
     */

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable("id") final int id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<Categorie> categorie = categorieService.getCategorie(id); //Optional -> encapsule un objet dont la valeur peut être null
        if (categorie.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(categorie.get());  //recupere la valeur de categorie
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCategorie(@RequestBody Categorie categorie) {         // deserialise les JSON dans un langage Java -> regroupe des données séparées dans un meme flux
        // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de Categorie
        categorieService.saveCategorie(categorie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable("id") final int id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        Optional<Categorie> optCategorie = categorieService.getCategorie(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optCategorie.isPresent()){
            categorieService.deleteCategorie(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategorie(@PathVariable("id") final int id, @RequestBody Categorie categorie) { //categorie contenu dans le body
        Optional<Categorie> optCategorie = categorieService.getCategorie(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optCategorie.isPresent()) {
            Categorie currentCategorie = optCategorie.get();

            //recupere les variables du categorie fourni en parametre pour les manipuler
            String nom = categorie.getNom();

            if (nom != null) {
                currentCategorie.setNom(nom);
            }
            categorieService.saveCategorie(currentCategorie);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
