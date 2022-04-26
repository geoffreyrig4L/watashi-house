package com.projetb3.api.controller;

import com.projetb3.api.model.Utilisateur;
import com.projetb3.api.service.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public ResponseEntity<Page<Utilisateur>> getAllUtilisateurs(@RequestParam("page") final Optional<Integer> page,
                                                                @RequestParam("sortBy") final Optional<String> sortBy,
                                                                @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Utilisateur> listeUtilisateurs = utilisateurService.getAllUtilisateurs(page, sortBy, orderBy);
        return ResponseEntity.ok(listeUtilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable("id") final int id) { //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateur(id);
        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createUtilisateur(@RequestBody Utilisateur utilisateur) { // le JSON saisi dans le body sera utiliser pour générer une instance d'Utilisateur
        utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable("id") final int id) {
        Optional<Utilisateur> optUtilisateur = utilisateurService.getUtilisateur(id);
        if (optUtilisateur.isPresent()){
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUtilisateur(@PathVariable("id") final int id, @RequestBody Utilisateur modification) {
        Optional<Utilisateur> optUtilisateur = utilisateurService.getUtilisateur(id);
        if (optUtilisateur.isPresent()) {
            Utilisateur current = optUtilisateur.get();
            if (modification.getId() != 0) {
                current.setId(modification.getId());
            }
            if (modification.getCivilite() != null) {
                current.setCivilite(modification.getCivilite());
            }
            if (modification.getPrenom() != null) {
                current.setPrenom(modification.getPrenom());
            }
            if (modification.getNom() != null) {
                current.setNom(modification.getNom());
            }
            if (modification.getEmail() != null) {
                current.setEmail(modification.getEmail());
            }
            if (modification.getMdp() != null) {
                current.setMdp(modification.getMdp());
            }
            if (modification.getTel() != null) {
                current.setTel(modification.getTel());
            }
            if(modification.getAdresse() != null) {
                current.setAdresse(modification.getAdresse());
            }
            if(modification.getCodepostal() != null) {
                current.setCodepostal(modification.getCodepostal());
            }
            if(modification.getVille() != null){
                current.setVille(modification.getVille());
            }
            if (modification.getPays() != null) {
                current.setPays(modification.getPays());
            }
            if (modification.getTypeuser() != null) {
                current.setTypeuser(modification.getTypeuser());
            }
            utilisateurService.saveUtilisateur(current);
            return ResponseEntity.ok().body("L'utilisateur " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.badRequest().body("L'utilisateur est introuvable.");
    }
}
