package com.projetb3.api.controller;

import com.projetb3.api.model.Article;
import com.projetb3.api.service.ArticleService;
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
    public ResponseEntity<Page<Article>> getAllArticles(@RequestParam("page") final Optional<Integer> page,
                                                        @RequestParam("sortBy") final Optional<String> sortBy,
                                                        @RequestParam("orderBy") final Optional<String> orderBy) {
        Page<Article> listeArticles = articleService.getAllArticles(page, sortBy, orderBy);
        return ResponseEntity.ok(listeArticles);
    }

    @GetMapping("/couleur={couleur}")
    public ResponseEntity<Page<Article>> getArticlesFiltreesParCouleur(@RequestParam("page") final Optional<Integer> page,
                                                                       @RequestParam("sortBy") final Optional<String> sortBy,
                                                                       @RequestParam("orderBy") final Optional<String> orderBy,
                                                                       @PathVariable("couleur") final String couleur) {
        return ResponseEntity.ok(articleService.getArticlesFiltreesParCouleur(page,sortBy,orderBy,couleur));
    }

    @GetMapping("/prix")
    public ResponseEntity<Page<Article>> getArticlesFiltreesParCouleur(@RequestParam("page") final Optional<Integer> page,
                                                                       @RequestParam("sortBy") final Optional<String> sortBy,
                                                                       @RequestParam("orderBy") final Optional<String> orderBy,
                                                                       @RequestParam("min") final int min,
                                                                       @RequestParam("max") final int max) {
        return ResponseEntity.ok(articleService.getArticlesFiltreesParPrix(page,sortBy,orderBy,min, max));
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
        if (article.getCategories().isEmpty()) {
            return ResponseEntity.ok().body("L'article doit être associé à une catégorie. Catégorie : " + article.getCategories());
        }
        articleService.saveArticle(article);
        return ResponseEntity.ok().body("L'article a été crée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") final int id) {
        Optional<Article> optArticle = articleService.getArticle(id);
        if (optArticle.isPresent()) {
            articleService.deleteArticle(id);
            return ResponseEntity.ok().body("L'article a été supprimé.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable("id") final int id, @RequestBody Article modification) {
        Optional<Article> optArticle = articleService.getArticle(id);
        if (optArticle.isPresent()) {
            Article current = optArticle.get();
            if (modification.getNom() != null) {
                current.setNom(modification.getNom());
            }
            if (modification.getDescription() != null) {
                current.setDescription(modification.getDescription());
            }
            if (modification.getImages1() != null) {
                current.setImages1(modification.getImages1());
            }
            if (modification.getImages2() != null) {
                current.setImages2(modification.getImages2());
            }
            if (modification.getImages3() != null) {
                current.setImages3(modification.getImages3());
            }
            if (modification.getImages4() != null) {
                current.setImages1(modification.getImages4());
            }
            if (modification.getStock() != 0) {
                current.setStock(modification.getStock());
            }
            if (modification.getCouleur() != null) {
                current.setCouleur(modification.getCouleur());
            }
            if (modification.getPrix() != 0) {
                current.setPrix(modification.getPrix());
            }
            articleService.saveArticle(current);
            return ResponseEntity.ok().body("L'article " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.notFound().build();
    }
}
