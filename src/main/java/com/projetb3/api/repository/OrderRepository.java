package com.projetb3.api.repository;

import com.projetb3.api.model.Item;
import com.projetb3.api.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE articles SET stock = stock - 1 WHERE id_article = :id_item", nativeQuery = true)
    void updateStock(int id_item);

    @Query(value = "SELECT a.prix FROM articles a WHERE a.id_article = :id_item", nativeQuery = true)
    int getPriceOfItem(int id_item);

    @Query(value = "SELECT c.* FROM commandes c WHERE c.utilisateur_id = :id_user", nativeQuery = true)
    List<Order> getOrdersOfUsers(@Param("id_user") int id_user);

    @Query(value = "SELECT u.prenom FROM utilisateurs u WHERE u.id_utilisateur = :id_user", nativeQuery = true)
    Optional<String> getFirstnameOfUser(@Param("id_user") int id_user);
}
