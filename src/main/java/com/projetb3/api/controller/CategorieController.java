package com.projetb3.api.controller;

import com.projetb3.api.model.Categorie;
import com.projetb3.api.model.SousCategorie;
import com.projetb3.api.service.CategorieService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategorieController {

    private CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Categorie>> getAllCategories() {
        Iterable<Categorie> listeCategories = categorieService.getAllCategories();
        return ResponseEntity.ok(listeCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable("id") final int id) {
        Optional<Categorie> categorie = categorieService.getCategorie(id);
        if (categorie.isPresent()) {
            return ResponseEntity.ok(categorie.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createCategorie(@RequestBody Categorie categorie) {
        categorieService.saveCategorie(categorie);
        return ResponseEntity.ok().body("La catégorie a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategorie(@PathVariable("id") final int id) {
        Optional<Categorie> optCategorie = categorieService.getCategorie(id);
        if (optCategorie.isPresent()) {
            categorieService.deleteCategorie(id);
            return ResponseEntity.ok().body("La catégorie a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategorie(@PathVariable("id") final int id, @RequestBody Categorie modification) {
        Optional<Categorie> optCategorie = categorieService.getCategorie(id);
        if (optCategorie.isPresent()) {
            Categorie current = optCategorie.get();
            if (modification.getNom() != null) {
                current.setNom(modification.getNom());
            }
            categorieService.saveCategorie(current);
            return ResponseEntity.ok().body("La catégorie " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/piece={id_piece}")
    public ResponseEntity<List<Categorie>> getArticlesDUnePiece(@PathVariable("id_piece") final int id_piece) {
        List<Categorie> listeCategories = categorieService.getCategoriesDUnePiece(id_piece);
        return ResponseEntity.ok(listeCategories);
    }

}
