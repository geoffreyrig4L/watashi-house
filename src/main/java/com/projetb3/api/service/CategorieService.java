package com.projetb3.api.service;

import com.projetb3.api.model.Categorie;
import com.projetb3.api.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public Optional<Categorie> getCategorie(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return categorieRepository.findById(id);
    }

    public Iterable<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public void deleteCategorie(final int id) {
        categorieRepository.deleteById(id);
    }

    public Categorie saveCategorie(Categorie categorie) {
        Categorie savedCategorie = categorieRepository.save(categorie);
        return savedCategorie;
    }
}
