package com.projetb3.api.repository;

import com.projetb3.api.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Integer> {

    @Query(value = "SELECT * FROM articles a WHERE a.couleur=:couleur", nativeQuery = true)
    List<Article> articlesParCouleur(@Param("couleur") String couleur);

    @Query(value = "SELECT * FROM articles a HAVING a.prix > :min AND a.prix < :max", nativeQuery = true)
    List<Article> articlesParPrix(int min, int max);

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
    List<Article> articlesDUneCategorie(@Param("id_categorie") int id_categorie);

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
    List<Article> articlesDUneSousCategorie(@Param("id_souscategorie") int id_souscategorie);

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
    List<Article> articlesDUnePiece(@Param("id_piece") int id_piece);
}
