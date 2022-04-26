package com.projetb3.api.service;

import com.projetb3.api.model.Utilisateur;
import com.projetb3.api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired //injection automatique des données
    private UtilisateurRepository utilisateurRepository;

    public Optional<Utilisateur> getUtilisateur(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return utilisateurRepository.findById(id);
    }

    public Page<Utilisateur> getAllUtilisateurs(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return utilisateurRepository.findAll(
                PageRequest.of(                                                       //Pour créer la page
                        page.orElse(0),                                         //si page est null = on commence à la page 0
                        40,                                                      //taille de la page
                        Orderer.getOrder(orderBy), sortBy.orElse("id")                  //si sortBy est null = on trie par id
                )
        );
    }

    public void deleteUtilisateur(final int id) {
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return savedUtilisateur;
    }
}
