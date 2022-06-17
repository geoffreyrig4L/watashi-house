package com.projetb3.api.repository;

import com.projetb3.api.model.Cart;
import com.projetb3.api.model.Favorite;
import com.projetb3.api.model.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorite,Integer> {

    @Query(value = "select * from favoris f where f.utilisateur_id = :id_utilisateur", nativeQuery = true)
    Optional<Favorite> favoritesOfUser(int id_utilisateur);

    @Transactional
    @Modifying
    @Query(value = "delete from favoris_articles where article_id = :id_item and favori_id = :id_favorite " , nativeQuery = true)
    void deleteItemOfFavorites(int id_item, int id_favorite);

    @Transactional
    @Modifying
    @Query(value = "delete from favoris_articles where favori_id = :id_favorite " , nativeQuery = true)
    void deleteAllItemsOfFavorites(int id_favorite);

    @Transactional
    @Modifying
    @Query(value = "insert into favoris_articles values (:id_favorite,:id_item)" , nativeQuery = true)
    void addItemInFavorites(int id_item, int id_favorite);
}
