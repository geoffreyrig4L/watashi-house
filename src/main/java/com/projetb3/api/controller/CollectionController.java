package com.projetb3.api.controller;

import com.projetb3.api.model.Collection;
import com.projetb3.api.service.CollectionService;
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
    public ResponseEntity<Iterable<Collection>> getAll() {
        Iterable<Collection> collectionsList = collectionService.getAll();
        return ResponseEntity.ok(collectionsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> get(@PathVariable("id") final int id) {
        Optional<Collection> collection = collectionService.get(id);
        if (collection.isPresent()) {
            return ResponseEntity.ok(collection.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Collection collection) {
        collectionService.save(collection);
        return ResponseEntity.ok().body("La collection a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<Collection> optCollection = collectionService.get(id);
        if (optCollection.isPresent()) {
            collectionService.deleteCollection(id);
            return ResponseEntity.ok().body("La collection a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Collection modified) {
        Optional<Collection> optCollection = collectionService.get(id);
        if (optCollection.isPresent()) {
            Collection current = optCollection.get();
            if (modified.getName() != null) {
                current.setName(modified.getName());
            }
            collectionService.save(current);
            return ResponseEntity.ok().body("La collection " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }
}
