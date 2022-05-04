package com.projetb3.api.service;

import com.projetb3.api.model.Collection;
import com.projetb3.api.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    public Optional<Collection> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return collectionRepository.findById(id);
    }

    public Iterable<Collection> getAll() {
        return collectionRepository.findAll();
    }

    public void deleteCollection(final int id) {
        collectionRepository.deleteById(id);
    }

    public Collection save(Collection collection) {
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }
}
