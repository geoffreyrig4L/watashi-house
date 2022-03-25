package com.projetb3.api_watashihouse.service;

import com.projetb3.api_watashihouse.model.Article;
import com.projetb3.api_watashihouse.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Optional<Article> getArticle(final int id_article){return articleRepository.findById(id_article); }

    public Page<Article> getAllArticles (Optional<Integer> page, Optional<String> sortBy){
        return articleRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        40,
                        Sort.Direction.ASC, sortBy.orElse("id_article")
                )
        );
    }

    public void deleteArticle(final int id_article){articleRepository.deleteById(id_article);}

    public Article saveArticle(Article article){
        return articleRepository.save(article);
    }
}
