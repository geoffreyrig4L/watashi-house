package com.projetb3.api.service;

import com.projetb3.api.model.CarteDePaiement;
import com.projetb3.api.repository.CarteDePaiementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CarteDePaiementService {

    @Autowired //injection automatique des données
    private CarteDePaiementRepository carteRepository;

    public Optional<CarteDePaiement> getCarteDePaiement(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return carteRepository.findById(id);
    }

    public Page<CarteDePaiement> getAllCartesDePaiement(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return carteRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        40,
                        Orderer.getOrder(orderBy), sortBy.orElse("id")
                )
        );
    }

    public void deleteCarteDePaiement(final int id) {
        carteRepository.deleteById(id);
    }

    public void saveCarteDePaiement(CarteDePaiement carte) {
        carteRepository.save(carte);
    }
}
