package com.projetb3.api.controller;

import com.projetb3.api.model.SousCategorie;
import com.projetb3.api.service.SousCategorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sous-categories")
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

    @GetMapping("/categorie={id_categorie}")
    public ResponseEntity<List<SousCategorie>> getArticlesDUneCategorie(@PathVariable("id_categorie") final int id_categorie) {
        List<SousCategorie> listeSousCategories = sousCategorieService.getSousCategoriesDUneCategorie(id_categorie);
        return ResponseEntity.ok(listeSousCategories);
    }

    @GetMapping("/piece={id_piece}")
    public ResponseEntity<List<SousCategorie>> getArticlesDUnePiece(@PathVariable("id_piece") final int id_piece) {
        List<SousCategorie> listeSousCategories = sousCategorieService.getSousCategoriesDUnePiece(id_piece);
        return ResponseEntity.ok(listeSousCategories);
    }

}

