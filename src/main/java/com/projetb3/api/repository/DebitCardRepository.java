package com.projetb3.api.repository;

import com.projetb3.api.model.DebitCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends CrudRepository<DebitCard,Integer> {

}
