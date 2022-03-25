package com.projetb3.api_watashihouse.service;

import com.projetb3.api_watashihouse.model.Collection;
import com.projetb3.api_watashihouse.repository.CollectionRepository;
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

    public Page<Collection> getAllCollections (Optional<Integer> page, Optional<String> sortBy) {
        return collectionRepository.findAll(
                PageRequest.of( //Pour créer la page
                        page.orElse(0), //si page est null = on commence à la page 0
                        40,  //taille de la page
                        Sort.Direction.ASC, sortBy.orElse("id_collection") //trier par ordre croissant, avec le param sortBy si il est null = on trie par id
                )
        );
    }

    public void deleteCollection(final int id) {
        collectionRepository.deleteById(id);
    }

    public Collection saveCollection(Collection collection) {           //creer une instance de la table et genere automatiquement l'id
        Collection savedCollection = collectionRepository.save(collection);
        return savedCollection;
    }
}
