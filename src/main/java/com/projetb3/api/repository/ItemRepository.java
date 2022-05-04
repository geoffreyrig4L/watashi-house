package com.projetb3.api.repository;

import com.projetb3.api.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

    @Query(value = "SELECT * FROM articles a WHERE a.couleur=:couleur", nativeQuery = true)
    List<Item> itemsByColor(@Param("couleur") String couleur);

    @Query(value = "SELECT * FROM articles a HAVING a.prix > :min AND a.prix < :max", nativeQuery = true)
    List<Item> itemsByPrice(int min, int max);

    @Query(value = "SELECT DISTINCT a.id_article," +
            " a.nom," +
            " a.details," +
            " a.images1," +
            " a.images2," +
            " a.images3," +
            " a.images4," +
            " a.couleur," +
            " a.prix," +
            " a.stock," +
            " a.collection_id " +
            "FROM articles a, categories_articles ca " +
            "WHERE a.id_article = ca.article_id AND ca.categorie_id = :id_categorie", nativeQuery = true)
    List<Item> itemsOfCategory(@Param("id_categorie") int id_category);

    @Query(value = "SELECT DISTINCT a.id_article," +
            " a.nom," +
            " a.details," +
            " a.images1," +
            " a.images2," +
            " a.images3," +
            " a.images4," +
            " a.couleur," +
            " a.prix," +
            " a.stock," +
            " a.collection_id " +
            "FROM articles a, souscategories_articles sca " +
            "WHERE a.id_article = ca.article_id AND sca.souscategorie_id = :id_souscategorie", nativeQuery = true)
    List<Item> itemsOfSubCategory(@Param("id_souscategorie") int id_subCategory);

    @Query(value = "SELECT DISTINCT a.id_article," +
            " a.nom," +
            " a.details," +
            " a.images1," +
            " a.images2," +
            " a.images3," +
            " a.images4," +
            " a.couleur," +
            " a.prix," +
            " a.stock," +
            " a.collection_id " +
            "FROM articles a, pieces_articles pa " +
            "WHERE a.id_article = pa.article_id AND pa.piece_id = :id_piece", nativeQuery = true)
    List<Item> itemsOfRoom(@Param("id_piece") int id_piece);
}
