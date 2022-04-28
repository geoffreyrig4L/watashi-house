package com.projetb3.api.service;

import com.projetb3.api.model.SousCategorie;
import com.projetb3.api.repository.SousCategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SousCategorieService {

    @Autowired //injection automatique des données
    private SousCategorieRepository sousCategorieRepository;

    public Optional<SousCategorie> getSousCategorie(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return sousCategorieRepository.findById(id);
    }

    public Iterable<SousCategorie> getAllSousCategories() {
        return sousCategorieRepository.findAll();
    }

    public void deleteSousCategorie(final int id) {
        sousCategorieRepository.deleteById(id);
    }

    public SousCategorie saveSousCategorie(SousCategorie sousCategorie) {
        SousCategorie savedSousCategorie = sousCategorieRepository.save(sousCategorie);
        return savedSousCategorie;
    }
}
