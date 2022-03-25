package com.projetb3.api_watashihouse.service;

import com.projetb3.api_watashihouse.model.Commande;
import com.projetb3.api_watashihouse.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public Optional<Commande> getCommande(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return commandeRepository.findById(id);
    }

    public Page<Commande> getAllCommandes(Optional<Integer> page, Optional<String> sortBy) {
        return commandeRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        10,
                        Sort.Direction.ASC, sortBy.orElse("id_commande")
                )
        );
    }

    public void deleteCommande(final int id) {
        commandeRepository.deleteById(id);
    }

    public Commande saveCommande(Commande commande) {           //creer une instance de la table et genere automatiquement l'id
        return commandeRepository.save(commande);
    }

}
