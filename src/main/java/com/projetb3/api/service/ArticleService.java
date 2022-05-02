package com.projetb3.api.service;

import com.projetb3.api.model.Article;
import com.projetb3.api.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Optional<Article> getArticle(final int id_article) {
        return articleRepository.findById(id_article);
    }

    public Page<Article> getAllArticles(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy) {
        return articleRepository.findAll(PageRequest.of(
                        page.orElse(0),
                        20,
                        getOrder(orderBy),
                        sortBy.orElse("id")
                )
        );
    }

    public void deleteArticle(final int id_article) {
        articleRepository.deleteById(id_article);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Page<Article> getArticlesFiltreesParCouleur(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, String couleur) {
        List<Article> listeArticles = articleRepository.articlesParCouleur(couleur);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(listeArticles, pageable, listeArticles.size());
    }

    public Page<Article> getArticlesFiltreesParPrix(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int min, int max) {
        List<Article> listeArticles = articleRepository.articlesParPrix(min, max);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(listeArticles, pageable, listeArticles.size());
    }

    public static Sort.Direction getOrder(Optional<String> orderBy) {
        if (orderBy.isPresent() && orderBy.get().equals("ASC")) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    public Page<Article> getArticlesDUneCategorie(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int id_categorie) {
        List<Article> listeArticles = articleRepository.articlesDUneCategorie(id_categorie);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(listeArticles, pageable, listeArticles.size());
    }

    public Page<Article> getArticlesDUneSousCategorie(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int id_souscategorie) {
        List<Article> listeArticles = articleRepository.articlesDUneSousCategorie(id_souscategorie);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(listeArticles, pageable, listeArticles.size());
    }
    public Page<Article> getArticlesDUnePiece(Optional<Integer> page, Optional<String> sortBy, Optional<String> orderBy, int id_piece) {
        List<Article> listeArticles = articleRepository.articlesDUnePiece(id_piece);
        Pageable pageable = PageRequest.of(page.orElse(0), 20, getOrder(orderBy),sortBy.orElse("id"));
        return new PageImpl<>(listeArticles, pageable, listeArticles.size());
    }
}