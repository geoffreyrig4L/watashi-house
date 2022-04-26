package com.projetb3.api.service;

import com.projetb3.api.model.Commande;
import com.projetb3.api.repository.CommandeRepository;
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

    public Page<Commande> getAllCommandes(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return commandeRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        10,
                        Orderer.getOrder(orderBy), sortBy.orElse("id")
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
