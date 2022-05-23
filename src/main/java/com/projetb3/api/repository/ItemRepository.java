package com.projetb3.api.repository;

import com.projetb3.api.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

    @Query(value = "SELECT * FROM articles a WHERE a.couleur=:couleur",
            countQuery = "SELECT COUNT(*) FROM articles a WHERE a.couleur=:couleur",
            nativeQuery = true)
    Page<Item> itemsByColor(@Param("couleur") String couleur, Pageable pageable);

    @Query(value = "SELECT * FROM articles a HAVING a.prix > :min AND a.prix < :max",
            countQuery = "SELECT COUNT(*) FROM articles a WHERE a.prix > :min AND a.prix < :max",
            nativeQuery = true)
    Page<Item> itemsByPrice(int min, int max, Pageable pageable);

    @Query(value = "SELECT DISTINCT a.* FROM articles a, categories_articles ca WHERE a.id_article = ca.article_id AND ca.categorie_id = :id_categorie",
            countQuery = "SELECT COUNT(*) FROM articles a, categories_articles ca WHERE a.id_article = ca.article_id AND ca.categorie_id = :id_categorie",
            nativeQuery = true)
    Page<Item> itemsOfCategory(@Param("id_categorie") int id_category, Pageable pageable);

    @Query(value = "SELECT DISTINCT a.* FROM articles a, souscategories_articles sca WHERE a.id_article = sca.article_id AND sca.souscategorie_id = :id_souscategorie",
            countQuery = "SELECT COUNT(*) FROM articles a, souscategories_articles sca WHERE a.id_article = sca.article_id AND sca.souscategorie_id = :id_souscategorie",
            nativeQuery = true)
    Page<Item> itemsOfSubCategory(@Param("id_souscategorie") int id_subCategory, Pageable pageable);

    @Query(value = "SELECT DISTINCT a.* FROM articles a, pieces_articles pa WHERE a.id_article = pa.article_id AND pa.piece_id = :id_piece",
            countQuery = "SELECT COUNT(*) FROM articles a, pieces_articles pa WHERE a.id_article = pa.article_id AND pa.piece_id = :id_piece",
            nativeQuery = true)
    Page<Item> itemsOfRoom(@Param("id_piece") int id_piece, Pageable pageable);
}
