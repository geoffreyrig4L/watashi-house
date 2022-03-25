package com.projetb3.api_watashihouse.service;

import com.projetb3.api_watashihouse.model.Utilisateur;
import com.projetb3.api_watashihouse.repository.UtilisateurRepository;
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

    public Page<Utilisateur> getAllUtilisateurs (Optional<Integer> page, Optional<String> sortBy) {
        return utilisateurRepository.findAll(
                PageRequest.of( //Pour créer la page
                        page.orElse(0), //si page est null = on commence à la page 0
                        40,  //taille de la page
                        Sort.Direction.ASC, sortBy.orElse("id_utilisateur") //trier par ordre croissant, avec le param sortBy si il est null = on trie par id
                )
        );
    }

    public void deleteUtilisateur(final int id) {
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {           //creer une instance de la table et genere automatiquement l'id
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return savedUtilisateur;
    }
}
