package com.projetb3.api.controller;

import com.projetb3.api.model.Opinion;
import com.projetb3.api.service.OpinionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/avis")
public class OpinionController {

    private OpinionService opinionService;

    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Opinion>> getAll() {
        Iterable<Opinion> listOpinion = opinionService.getAll();
        return ResponseEntity.ok(listOpinion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opinion> get(@PathVariable("id") final int id) {
        Optional<Opinion> opinion = opinionService.get(id);
        return opinion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<Opinion> optOpinion = opinionService.get(id);
        if (optOpinion.isPresent()) {
            opinionService.delete(id);
            return ResponseEntity.ok().body("L'avis a été supprimé.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/article={id}")
    public ResponseEntity<List<Opinion>> getOpinionsOfItem(@PathVariable("id") final int id) {
        List<Opinion> opinions = opinionService.getOpinionsOfItem(id);
        return ResponseEntity.ok(opinions);
    }

    @GetMapping("/moyenne/article={id}")
    public ResponseEntity<Float> getAverageOfItem(@PathVariable("id") final int id) {
        return ResponseEntity.ok(opinionService.getAverageOfItem(id));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Opinion opinion) {
        System.out.println(opinion.toString());
        opinion.setDateOfPublication(LocalDateTime.now());
        opinionService.save(opinion);
        return ResponseEntity.ok().body("L'avis a été crée.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Opinion modified) {
        Optional<Opinion> optOpinion = opinionService.get(id);
        if (optOpinion.isPresent()) {
            Opinion current = optOpinion.get();
            if(modified.getNote() >= 0){
                current.setNote(current.getNote());
            }
            if(modified.getComment() != null){
                current.setComment(current.getComment());
            }
            opinionService.save(current);
            return ResponseEntity.ok().body("L'avis " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.badRequest().body("L'avis est introuvable.");
    }
}
