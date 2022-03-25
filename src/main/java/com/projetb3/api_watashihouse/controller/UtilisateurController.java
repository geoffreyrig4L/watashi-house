package com.projetb3.api_watashihouse.controller;

import com.projetb3.api_watashihouse.model.Utilisateur;
import com.projetb3.api_watashihouse.service.UtilisateurService;
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
    public ResponseEntity<Page<Utilisateur>> getAllUtilisateursToWatch(@RequestParam("page") final Optional<Integer> page, @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<Utilisateur> utilisateurList = utilisateurService.getAllUtilisateurs(page, sortBy);
        return ResponseEntity.ok(utilisateurList);
    }

    /*
        @RequestParam recupere des infos concernant les ressources, tout ce qu'on peut trouver apres le ?, ses infos servent principalement de filtrage
        @PathVariable récupère la ressource directement soit les champs contenu dans notre bdd (id, title, date_released)
     */

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
    public ResponseEntity<Void> updateUtilisateur(@PathVariable("id") final int id, @RequestBody Utilisateur utilisateur) { //utilisateur contenu dans le body
        Optional<Utilisateur> optUtilisateur = utilisateurService.getUtilisateur(id);

        if (optUtilisateur.isPresent()) {
            Utilisateur currentUtilisateur = optUtilisateur.get();

            //recupere les variables du utilisateur fourni en parametre pour les manipuler
            int newId = utilisateur.getId();
            String civilite = utilisateur.getCivilite();
            String prenom = utilisateur.getPrenom();
            String nom = utilisateur.getNom();
            String email = utilisateur.getEmail();
            String mdp = utilisateur.getMdp();
            String tel = utilisateur.getTel();
            String adresse_livraison = utilisateur.getAdresse_livraison();
            String adresse_facturation = utilisateur.getAdresse_facturation();
            String pays = utilisateur.getPays();
            String type_user = utilisateur.getType_user();
            if (newId != 0) {
                currentUtilisateur.setId(newId);
            }
            if (civilite != null) {
                currentUtilisateur.setCivilite(civilite);
            }
            if (prenom != null) {
                currentUtilisateur.setPrenom(prenom);
            }
            if (nom != null) {
                currentUtilisateur.setNom(nom);
            }
            if (email != null) {
                currentUtilisateur.setEmail(email);
            }
            if (mdp != null) {
                currentUtilisateur.setMdp(mdp);
            }
            if (tel != null) {
                currentUtilisateur.setTel(tel);
            }
            if(adresse_livraison != null) {
                currentUtilisateur.setAdresse_livraison(adresse_livraison);
            }
            if (adresse_facturation != null) {
                currentUtilisateur.setAdresse_facturation(adresse_facturation);
            }
            if (pays != null) {
                currentUtilisateur.setPays(pays);
            }
            if (type_user != null) {
                currentUtilisateur.setType_user(type_user);
            }

            utilisateurService.saveUtilisateur(currentUtilisateur);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
