package com.projetb3.api.repository;

import com.projetb3.api.model.DebitCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends CrudRepository<DebitCard,Integer> {

    @Query(value = "select * from cartes c where c.utilisateur_id = :id_utilisateur", nativeQuery = true)
    Iterable<DebitCard> cardsOfUser(int id_utilisateur);
}
