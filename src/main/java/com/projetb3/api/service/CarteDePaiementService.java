package com.projetb3.api.service;

import com.projetb3.api.model.CarteDePaiement;
import com.projetb3.api.repository.CarteDePaiementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CarteDePaiementService {

    @Autowired
    private CarteDePaiementRepository carteRepository;

    public Optional<CarteDePaiement> getCarteDePaiement(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return carteRepository.findById(id);
    }

    public Iterable<CarteDePaiement> getAllCartesDePaiement() {
        return carteRepository.findAll();
    }

    public void deleteCarteDePaiement(final int id) {
        carteRepository.deleteById(id);
    }

    public void saveCarteDePaiement(CarteDePaiement carte) {
        carteRepository.save(carte);
    }
}
