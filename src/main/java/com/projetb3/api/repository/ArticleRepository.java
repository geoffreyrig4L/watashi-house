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
}
