package com.projetb3.api.service;

import com.projetb3.api.model.Collection;
import com.projetb3.api.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectionService {

    @Autowired //injection automatique des données
    private CollectionRepository collectionRepository;

    public Optional<Collection> getCollection(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return collectionRepository.findById(id);
    }

    public Page<Collection> getAllCollections(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return collectionRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        40,
                        Orderer.getOrder(orderBy), sortBy.orElse("id")
                )
        );
    }

    public void deleteCollection(final int id) {
        collectionRepository.deleteById(id);
    }

    public Collection saveCollection(Collection collection) {
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }
}
