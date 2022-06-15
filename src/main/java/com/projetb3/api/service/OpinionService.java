package com.projetb3.api.service;

import com.projetb3.api.model.Item;
import com.projetb3.api.model.Opinion;
import com.projetb3.api.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    public Iterable<Opinion> getAll(){
        return opinionRepository.findAll();
    }

    public Optional<Opinion> get(int id) {
        return opinionRepository.findById(id);
    }

    public void delete(int id) {
        opinionRepository.deleteById(id);
    }

    public void save(Opinion opinion) {
        opinionRepository.save(opinion);
    }

    public List<Opinion> getOpinionsOfItem(int id) {
        return opinionRepository.opinionsOfItem(id);
    }

    public Float getAverageOfItem(int id) {
        return opinionRepository.averageNote(id);
    }
}
