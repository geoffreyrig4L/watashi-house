package com.projetb3.api.repository;

import com.projetb3.api.model.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Integer> {

    @Query(value = "select * from paniers p where p.utilisateur_id = :id_utilisateur", nativeQuery = true)
    Optional<Cart> cartOfUser(int id_utilisateur);

    @Transactional
    @Modifying
    @Query(value = "delete from paniers_articles where article_id = :id_item and panier_id = :id_cart " , nativeQuery = true)
    void deleteItemOfCart(int id_item, int id_cart);

    @Transactional
    @Modifying
    @Query(value = "delete from paniers_articles where panier_id = :id_cart " , nativeQuery = true)
    void deleteAllItemsOfCart(int id_cart);

    @Transactional
    @Modifying
    @Query(value = "insert into paniers_articles values (:id_cart,:id_item)" , nativeQuery = true)
    void addItemInCart(int id_item, int id_cart);
}
