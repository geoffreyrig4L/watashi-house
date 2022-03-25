package com.projetb3.api_watashihouse.service;

import com.projetb3.api_watashihouse.model.CarteDePaiement;
import com.projetb3.api_watashihouse.repository.CarteDePaiementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class CarteDePaiementService {

    @Autowired //injection automatique des données
    private CarteDePaiementRepository carteRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    public Optional<CarteDePaiement> getCarteDePaiement(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return carteRepository.findById(id);
    }

    public Page<CarteDePaiement> getAllCartesDePaiement(Optional<Integer> page, Optional<String> sortBy) {
        return carteRepository.findAll(
                PageRequest.of( //Pour créer la page
                        page.orElse(0), //si page est null = on commence à la page 0
                        40,  //taille de la page
                        Sort.Direction.ASC, sortBy.orElse("id_carte") //trier par ordre croissant, avec le param sortBy si il est null = on trie par id
                )
        );
    }

    public void deleteCarteDePaiement(final int id) {
        carteRepository.deleteById(id);
    }

    public void saveCarteDePaiement(CarteDePaiement carte){           //creer une instance de la table et genere automatiquement l'id
        carteRepository.save(carte);
    }
}
