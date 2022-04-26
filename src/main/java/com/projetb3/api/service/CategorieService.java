package com.projetb3.api.service;

import com.projetb3.api.model.Categorie;
import com.projetb3.api.repository.CategorieRepository;
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

    public Page<Categorie> getAllCategories(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return categorieRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        40,
                        Orderer.getOrder(orderBy), sortBy.orElse("id")
                )
        );
    }

    public void deleteCategorie(final int id) {
        categorieRepository.deleteById(id);
    }

    public Categorie saveCategorie(Categorie categorie) {
        Categorie savedCategorie = categorieRepository.save(categorie);
        return savedCategorie;
    }
}
