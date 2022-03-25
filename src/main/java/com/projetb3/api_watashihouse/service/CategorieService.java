package com.projetb3.api_watashihouse.service;

import com.projetb3.api_watashihouse.model.Categorie;
import com.projetb3.api_watashihouse.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired //injection automatique des données
    private CategorieRepository categorieRepository;

    public Optional<Categorie> getCategorie(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return categorieRepository.findById(id);
    }

    public Page<Categorie> getAllCategories (Optional<Integer> page, Optional<String> sortBy) {
        return categorieRepository.findAll(
                PageRequest.of( //Pour créer la page
                        page.orElse(0), //si page est null = on commence à la page 0
                        40,  //taille de la page
                        Sort.Direction.ASC, sortBy.orElse("id_categorie") //trier par ordre croissant, avec le param sortBy si il est null = on trie par id
                )
        );
    }

    public void deleteCategorie(final int id) {
        categorieRepository.deleteById(id);
    }

    public Categorie saveCategorie(Categorie categorie) {           //creer une instance de la table et genere automatiquement l'id
        Categorie savedCategorie = categorieRepository.save(categorie);
        return savedCategorie;
    }
}
