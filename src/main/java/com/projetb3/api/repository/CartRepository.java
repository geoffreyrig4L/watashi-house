package com.projetb3.api.repository;

import com.projetb3.api.model.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Integer> {

    @Query(value = "select * from paniers p where p.utilisateur_id = :id_utilisateur", nativeQuery = true)
    Optional<Cart> bucketOfUser(int id_utilisateur);
}
