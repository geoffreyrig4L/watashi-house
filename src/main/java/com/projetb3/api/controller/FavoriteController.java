package com.projetb3.api.controller;

import com.projetb3.api.model.Favorite;
import com.projetb3.api.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/favoris")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Favorite>> getAll(@RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<Favorite> favoritesList = favoriteService.getAll();
            return ResponseEntity.ok(favoritesList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favorite> get(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Favorite> favorite = favoriteService.get(id);
        if (favorite.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            return ResponseEntity.ok(favorite.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/utilisateur={id}")
    public ResponseEntity<Favorite> favoritesOfUser(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Favorite> favorite = favoriteService.favoritesOfUser(id);
        if (favorite.isPresent() && verifySenderOfRequest(token, Optional.of(favorite.get().getUser().getFirstname()))) {
            return ResponseEntity.ok(favorite.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable final int id, @RequestHeader("Authentication") final String token) {
        Optional<Favorite> optFavorite = favoriteService.get(id);
        if (optFavorite.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            favoriteService.delete(id);
            return ResponseEntity.ok().body("L'instance des favoris a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id_favorite}/supprimerArticle={id_item}")
    public ResponseEntity<String> deleteItemOfFavorites(@PathVariable("id_favorite") final int id_favorite,
                                                   @PathVariable("id_item") final int id_item,
                                                   @RequestHeader("Authentication") final String token) {
        Optional<Favorite> favorite = favoriteService.get(id_favorite);
        if (favorite.isPresent() && verifySenderOfRequest(token, Optional.of(favorite.get().getUser().getFirstname()))) {
            favoriteService.deleteItemOfFavorites(id_item, favorite.get().getId());
            favoriteService.save(favorite.get());
            return ResponseEntity.ok().body("L'article " + id_item + " a été supprimé des favoris " + favorite.get().getId());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id_favorite}/supprimerArticle")
    public ResponseEntity<String> deleteAllItemsOfFavorites(@PathVariable("id_favorite") final int id_favorite,
                                                       @RequestHeader("Authentication") final String token) {
        Optional<Favorite> favorite = favoriteService.get(id_favorite);
        if (favorite.isPresent() && verifySenderOfRequest(token, Optional.of(favorite.get().getUser().getFirstname()))) {
            favoriteService.deleteAllItemsOfFavorites(favorite.get().getId());
            favoriteService.save(favorite.get());
            return ResponseEntity.ok().body("Les favoris " + favorite.get().getId() + " ont été vidés.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id_favorite}/ajouterArticle={id_item}")
    public ResponseEntity<String> addItemInFavorites(@PathVariable("id_favorite") final int id_favorite,
                                                @PathVariable("id_item") final int id_item,
                                                @RequestHeader("Authentication") final String token) {
        Optional<Favorite> favorite = favoriteService.get(id_favorite);
        if (favorite.isPresent() && verifySenderOfRequest(token, Optional.of(favorite.get().getUser().getFirstname()))) {
            favoriteService.addItemOfFavorites(id_item, favorite.get().getId());
            favoriteService.save(favorite.get());
            return ResponseEntity.ok().body("L'article " + id_item + " a été ajouté aux favoris " + favorite.get().getId());
        }
        return ResponseEntity.notFound().build();
    }
}