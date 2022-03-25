package com.projetb3.api_watashihouse.controller;

import com.projetb3.api_watashihouse.model.Collection;
import com.projetb3.api_watashihouse.service.CollectionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/collections")
public class CollectionController {

    private CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<Page<Collection>> getAllCollectionsToWatch(
            @RequestParam("page") final Optional<Integer> page,
            @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<Collection> collectionList = collectionService.getAllCollections(page, sortBy);
        return ResponseEntity.ok(collectionList);
    }

    /*
        @RequestParam recupere des infos concernant les ressources, tout ce qu'on peut trouver apres le ?, ses infos servent principalement de filtrage
        @PathVariable récupère la ressource directement soit les champs contenu dans notre bdd (id, title, date_released)
     */

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable("id") final int id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<Collection> collection = collectionService.getCollection(id); //Optional -> encapsule un objet dont la valeur peut être null
        if (collection.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(collection.get());  //recupere la valeur de collection
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCollection(@RequestBody Collection collection) {         // deserialise les JSON dans un langage Java -> regroupe des données séparées dans un meme flux
        // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de Collection
        collectionService.saveCollection(collection);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable("id") final int id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        Optional<Collection> optCollection = collectionService.getCollection(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optCollection.isPresent()){
            collectionService.deleteCollection(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCollection(@PathVariable("id") final int id, @RequestBody Collection collection) { //collection contenu dans le body
        Optional<Collection> optCollection = collectionService.getCollection(id);  //Optional -> encapsule un objet dont la valeur peut être null

        if (optCollection.isPresent()) {
            Collection currentCollection = optCollection.get();

            //recupere les variables du collection fourni en parametre pour les manipuler
            String nom = collection.getNom();

            if (nom != null) {
                currentCollection.setNom(nom);
            }
            collectionService.saveCollection(currentCollection);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
