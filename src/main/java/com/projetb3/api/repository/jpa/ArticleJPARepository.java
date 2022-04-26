package com.projetb3.api.repository.jpa;

import com.projetb3.api.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleJPARepository extends JpaRepository<Article, Integer> {

    //List<Article> findArticleByid_categorie(int id_categorie);
}
