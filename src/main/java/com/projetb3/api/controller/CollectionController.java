package com.projetb3.api.controller;

import com.projetb3.api.model.Collection;
import com.projetb3.api.service.CollectionService;
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
    public ResponseEntity<Iterable<Collection>> getAllCollections() {
        Iterable<Collection> listeCollections = collectionService.getAllCollections();
        return ResponseEntity.ok(listeCollections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable("id") final int id) {
        Optional<Collection> collection = collectionService.getCollection(id);
        if (collection.isPresent()) {
            return ResponseEntity.ok(collection.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createCollection(@RequestBody Collection collection) {
        collectionService.saveCollection(collection);
        return ResponseEntity.ok().body("La collection a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCollection(@PathVariable("id") final int id) {
        Optional<Collection> optCollection = collectionService.getCollection(id);
        if (optCollection.isPresent()) {
            collectionService.deleteCollection(id);
            return ResponseEntity.ok().body("La collection a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCollection(@PathVariable("id") final int id, @RequestBody Collection modification) {
        Optional<Collection> optCollection = collectionService.getCollection(id);
        if (optCollection.isPresent()) {
            Collection current = optCollection.get();
            if (modification.getNom() != null) {
                current.setNom(modification.getNom());
            }
            collectionService.saveCollection(current);
            return ResponseEntity.ok().body("La collection " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }
}
