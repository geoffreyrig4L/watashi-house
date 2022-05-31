package com.projetb3.api.repository;

import com.projetb3.api.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE articles SET stock = stock - 1 WHERE id_article = :id_item", nativeQuery = true)
    void updateStock(int id_item);

    @Query(value = "SELECT a.prix FROM articles a WHERE a.id_article = :id_item", nativeQuery = true)
    int getPriceOfItem(int id_item);
}
