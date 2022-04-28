package com.projetb3.api.controller;

import com.projetb3.api.model.Avis;
import com.projetb3.api.service.AvisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class AvisController {

    private AvisService avisService;

    public AvisController(AvisService avisService) { this.avisService=avisService; }

    @GetMapping
    public ResponseEntity<Iterable<Avis>> getAllAvis(){
        Iterable<Avis> listeAvis = avisService.getAllAvis();
        return ResponseEntity.ok(listeAvis);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAvis(@PathVariable("id") final int id){
        Optional<Avis> optAvis = avisService.getAvis(id);
        if(optAvis.isPresent()){
            avisService.deleteAvis(id);
            return ResponseEntity.ok().body("L'avis a été supprimé.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createAvis(@RequestBody Avis avis) {
        avisService.saveArticle(avis);
        return ResponseEntity.ok().body("L'avis a été crée.");
    }
}
