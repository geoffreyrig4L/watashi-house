package com.projetb3.api.controller;

import com.projetb3.api.model.Opinion;
import com.projetb3.api.service.OpinionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/avis")
public class OpinionController {

    private OpinionService opinionService;

    public OpinionController(OpinionService opinionService) { this.opinionService=opinionService; }

    @GetMapping
    public ResponseEntity<Iterable<Opinion>> getAll(){
        Iterable<Opinion> listOpinion = opinionService.getAll();
        return ResponseEntity.ok(listOpinion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id){
        Optional<Opinion> optOpinion = opinionService.get(id);
        if(optOpinion.isPresent()){
            opinionService.delete(id);
            return ResponseEntity.ok().body("L'avis a été supprimé.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Opinion opinion) {
        opinionService.save(opinion);
        return ResponseEntity.ok().body("L'avis a été crée.");
    }
}
