package com.projetb3.api.controller;

import com.projetb3.api.model.SousCategorie;
import com.projetb3.api.service.SousCategorieService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/sous_categories")
public class SousCategorieController {

    private SousCategorieService sousCategorieService;

    public SousCategorieController(SousCategorieService sousCategorieService) {
        this.sousCategorieService = sousCategorieService;
    }

    @GetMapping
    public ResponseEntity<Iterable<SousCategorie>> getAllSousCategories() {
        Iterable<SousCategorie> listeSousCategories = sousCategorieService.getAllSousCategories();
        return ResponseEntity.ok(listeSousCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousCategorie> getSousCategorie(@PathVariable("id") final int id) {
        Optional<SousCategorie> sousCategorie = sousCategorieService.getSousCategorie(id);
        if (sousCategorie.isPresent()) {
            return ResponseEntity.ok(sousCategorie.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createSousCategorie(@RequestBody SousCategorie sousCategorie) {
        sousCategorieService.saveSousCategorie(sousCategorie);
        return ResponseEntity.ok().body("La catégorie a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSousCategorie(@PathVariable("id") final int id) {
        Optional<SousCategorie> optSousCategorie = sousCategorieService.getSousCategorie(id);
        if (optSousCategorie.isPresent()) {
            sousCategorieService.deleteSousCategorie(id);
            return ResponseEntity.ok().body("La catégorie a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSousCategorie(@PathVariable("id") final int id, @RequestBody SousCategorie modification) {
        Optional<SousCategorie> optSousCategorie = sousCategorieService.getSousCategorie(id);
        if (optSousCategorie.isPresent()) {
            SousCategorie current = optSousCategorie.get();
            if (modification.getNom() != null) {
                current.setNom(modification.getNom());
            }
            sousCategorieService.saveSousCategorie(current);
            return ResponseEntity.ok().body("La catégorie " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

}

