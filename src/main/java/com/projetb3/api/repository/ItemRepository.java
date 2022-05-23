package com.projetb3.api.repository;

import com.projetb3.api.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

    @Query(value = "SELECT DISTINCT * " +
            "FROM articles a, categories_articles ca, souscategories_articles sca, pieces_articles pa" +
            "WHERE a.couleur=:couleur " +
            "AND HAVING a.prix > :min AND a.prix < :max" +
            "AND WHERE a.id_article = sca.article_id AND sca.souscategorie_id = :id_souscategorie" +
            "AND WHERE a.id_article = sca.article_id AND sca.souscategorie_id = :id_souscategorie " +
            "AND WHERE a.id_article = pa.article_id AND pa.piece_id = :id_piece", nativeQuery = true)
    List<Item> itemsFiltered(@Param("couleur") String couleur,
                             @Param("id_categorie") int id_category,
                             @Param("id_souscategorie") int id_subCategory,
                             @Param("id_piece") int id_piece,
                             int min,
                             int max
    );
}
