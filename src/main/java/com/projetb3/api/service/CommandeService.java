package com.projetb3.api.service;

import com.projetb3.api.model.Commande;
import com.projetb3.api.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public Optional<Commande> getCommande(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return commandeRepository.findById(id);
    }

    public Iterable<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public void deleteCommande(final int id) {
        commandeRepository.deleteById(id);
    }

    public Commande saveCommande(Commande commande) {           //creer une instance de la table et genere automatiquement l'id
        return commandeRepository.save(commande);
    }

}
