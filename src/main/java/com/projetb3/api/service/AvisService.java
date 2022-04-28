package com.projetb3.api.service;

import com.projetb3.api.model.Avis;
import com.projetb3.api.repository.AvisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvisService {

    @Autowired
    private AvisRepository avisRepository;

    public Iterable<Avis> getAllAvis(){
        return avisRepository.findAll();
    }

    public Optional<Avis> getAvis(int id) {
        return avisRepository.findById(id);
    }

    public void deleteAvis(int id) {
        avisRepository.deleteById(id);
    }

    public void saveArticle(Avis avis) {
        avisRepository.save(avis);
    }
}
