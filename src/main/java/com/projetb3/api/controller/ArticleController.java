package com.projetb3.api.controller;

import com.projetb3.api.model.Article;
import com.projetb3.api.repository.jpa.ArticleJPARepository;
import com.projetb3.api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<Page<Article>> getAllArticlesToWatch(@RequestParam("page") final Optional<Integer> page, @RequestParam("sortBy") final Optional<String> sortBy) {
        Page<Article> articleList = articleService.getAllArticles(page, sortBy);
        return ResponseEntity.ok(articleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable("id") final int id) {
        Optional<Article> article = articleService.getArticle(id);
        if (article.isPresent()) {
            return ResponseEntity.ok(article.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody Article article) {
        System.out.println(article.toString());
        if(article.getCategories().isEmpty()){
            return ResponseEntity.ok().body("L'article doit être associé à une catégorie. Catégorie : " + article.getCategories());
        }
        articleService.saveArticle(article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") final int id) {
        Optional<Article> optArticle = articleService.getArticle(id);

        if (optArticle.isPresent()){
            articleService.deleteArticle(id);
            return ResponseEntity.ok().body("L'article a été supprimé.");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable("id") final int id, @RequestBody Article article) {
        Optional<Article> optArticle = articleService.getArticle(id);
        if (optArticle.isPresent()) {
            Article currentArticle = optArticle.get();
            String nom = article.getNom();
            String description = article.getDescription();
            String images = article.getImages();
            String couleur = article.getCouleur();
            int prix = article.getPrix();
            int nb_avis = article.getNb_avis();
            int note = article.getNote();
            int stock = article.getStock();
            if (nom != null) {
                currentArticle.setNom(nom);
            }
            if (description != null) {
                currentArticle.setDescription(description);
            }
            if (images != null) {
                currentArticle.setImages(images);
            }
            if (couleur != null) {
                currentArticle.setCouleur(couleur);
            }
            if (prix != 0) {
                currentArticle.setPrix(prix);
            }
            if (nb_avis != 0) {
                currentArticle.setNb_avis(nb_avis);
            }
            if (note != 0) {
                currentArticle.setNote(note);
            }
            if (stock != 0) {
                currentArticle.setNote(stock);
            }
            articleService.saveArticle(currentArticle);
            return ResponseEntity.ok().body("L'article a été modifié.");
        }
        return ResponseEntity.badRequest().build();
    }
}
