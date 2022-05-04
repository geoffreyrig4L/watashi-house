package com.projetb3.api.service;

import com.projetb3.api.model.DebitCard;
import com.projetb3.api.repository.DebitCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DebitCardService {

    @Autowired
    private DebitCardRepository debitCardRepository;

    public Optional<DebitCard> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return debitCardRepository.findById(id);
    }

    public Iterable<DebitCard> getAll() {
        return debitCardRepository.findAll();
    }

    public void delete(final int id) {
        debitCardRepository.deleteById(id);
    }

    public void save(DebitCard debitCard) {
        debitCardRepository.save(debitCard);
    }
}
