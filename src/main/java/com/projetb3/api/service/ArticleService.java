package com.projetb3.api.service;

import com.projetb3.api.model.Article;
import com.projetb3.api.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                        Orderer.getOrder(orderBy),
                        sortBy.orElse("id_article")
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
        Pageable pageable = PageRequest.of(page.orElse(0), 20, Orderer.getOrder(orderBy),sortBy.orElse("id_article"));
        return new PageImpl<>(listeArticles, pageable, listeArticles.size());
    }
}
