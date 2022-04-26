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
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable("id") final int id) {     //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateur(id);
        if (utilisateur.isPresent()) {   //si il existe dans la bdd
            return ResponseEntity.ok(utilisateur.get());  //recupere la valeur de utilisateur
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createUtilisateur(@RequestBody Utilisateur utilisateur) { // le JSON saisie par l'user dans le body sera donc utiliser pour générer une instance de Utilisateur
        utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable("id") final int id) {  //void sgnifie qu'il n'y a aucun objet dans le body
        Optional<Utilisateur> optUtilisateur = utilisateurService.getUtilisateur(id);
        if (optUtilisateur.isPresent()){
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUtilisateur(@PathVariable("id") final int id, @RequestBody Utilisateur utilisateur) { //utilisateur contenu dans le body
        Optional<Utilisateur> optUtilisateur = utilisateurService.getUtilisateur(id);

        if (optUtilisateur.isPresent()) {
            Utilisateur currentUtilisateur = optUtilisateur.get();
            if (utilisateur.getId() != 0) {
                currentUtilisateur.setId(utilisateur.getId());
            }
            if (utilisateur.getCivilite() != null) {
                currentUtilisateur.setCivilite(utilisateur.getCivilite());
            }
            if (utilisateur.getPrenom() != null) {
                currentUtilisateur.setPrenom(utilisateur.getPrenom());
            }
            if (utilisateur.getNom() != null) {
                currentUtilisateur.setNom(utilisateur.getNom());
            }
            if (utilisateur.getEmail() != null) {
                currentUtilisateur.setEmail(utilisateur.getEmail());
            }
            if (utilisateur.getMdp() != null) {
                currentUtilisateur.setMdp(utilisateur.getMdp());
            }
            if (utilisateur.getTel() != null) {
                currentUtilisateur.setTel(utilisateur.getTel());
            }
            if(utilisateur.getAdresse() != null) {
                currentUtilisateur.setAdresse(utilisateur.getAdresse());
            }
            if(utilisateur.getCodepostal() != null) {
                currentUtilisateur.setCodepostal(utilisateur.getCodepostal());
            }
            if(utilisateur.getVille() != null){
                currentUtilisateur.setVille(utilisateur.getVille());
            }
            if (utilisateur.getPays() != null) {
                currentUtilisateur.setPays(utilisateur.getPays());
            }
            if (utilisateur.getTypeuser() != null) {
                currentUtilisateur.setTypeuser(utilisateur.getTypeuser());
            }
            utilisateurService.saveUtilisateur(currentUtilisateur);
            return ResponseEntity.ok().body("L'utilisateur " + currentUtilisateur.getId() + " a bien été modifié.");
        }
        return ResponseEntity.badRequest().body("L'utilisateur est introuvable.");
    }
}
