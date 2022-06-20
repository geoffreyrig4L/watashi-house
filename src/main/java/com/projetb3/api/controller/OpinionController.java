package com.projetb3.api.controller;

import com.projetb3.api.model.Opinion;
import com.projetb3.api.service.OpinionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.projetb3.api.security.AuthenticationWithJWT.verifyJwt;
import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/avis")
public class OpinionController {

    private OpinionService opinionService;

    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Opinion>> getAll(@RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<Opinion> listOpinion = opinionService.getAll();
            return ResponseEntity.ok(listOpinion);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opinion> get(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Optional<Opinion> opinion = opinionService.get(id);
            return opinion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Opinion> optOpinion = opinionService.get(id);
        if (optOpinion.isPresent() && verifySenderOfRequest(token, Optional.of(optOpinion.get().getUser().getFirstname()))){
            opinionService.delete(id);
            saveNewNoteOfItem(optOpinion.get().getItem().getId());
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
    public ResponseEntity<String> create(@RequestBody Opinion opinion, @RequestHeader("Authentication") final String token) {
        if(verifyJwt(token) != null){
            opinion.setDateOfPublication(LocalDateTime.now());
            opinionService.save(opinion);
            saveNewNoteOfItem(opinion.getItem().getId());
            return ResponseEntity.ok().body("L'avis a été crée.");
        }
        return ResponseEntity.badRequest().body("🛑 Vous devez être connecté pour écrire un avis");
    }

    private void saveNewNoteOfItem(int id_opinion) {
        float avg = opinionService.getAverageOfItem(id_opinion);
        opinionService.setNoteOfItem(id_opinion, avg);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id,
                                         @RequestBody Opinion modified,
                                         @RequestHeader("Authentication") final String token) {
        Optional<Opinion> optOpinion = opinionService.get(id);
        if (optOpinion.isPresent() && verifySenderOfRequest(token, Optional.of(optOpinion.get().getUser().getFirstname()))) {
            Opinion current = optOpinion.get();
            if (modified.getNote() >= 0) {
                current.setNote(modified.getNote());
                saveNewNoteOfItem(modified.getItem().getId());
            }
            if (modified.getComment() != null) {
                current.setComment(modified.getComment());
            }
            opinionService.save(current);
            return ResponseEntity.ok().body("L'avis " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.badRequest().build();
    }
}
