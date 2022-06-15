package com.projetb3.api.controller;

import com.projetb3.api.model.Cart;
import com.projetb3.api.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/paniers")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Cart>> getAll() {
        Iterable<Cart> cartsList = cartService.getAll();
        return ResponseEntity.ok(cartsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> get(@PathVariable("id") final int id) {
        Optional<Cart> cart = cartService.get(id);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/utilisateur={id}")
    public ResponseEntity<Cart> cartOfUser(@PathVariable("id") final int id) {
        Optional<Cart> cart = cartService.cartOfUser(id);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Cart cart) {
        cartService.save(cart);
        return ResponseEntity.ok().body("Le panier a été crée.");
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable final int id) {
        Optional<Cart> optCart = cartService.get(id);
        if (optCart.isPresent()) {
            cartService.delete(id);
            return ResponseEntity.ok().body("Le panier a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id_cart}/supprimerArticle={id_item}")
    public ResponseEntity<String> deleteItemOfCart(@PathVariable("id_cart") final int id_cart, @PathVariable("id_item") final int id_item){
        Optional<Cart> optCart = cartService.get(id_cart);
        if (optCart.isPresent()) {
            Cart cart = optCart.get();
            cartService.deleteItemOfCart(id_item, cart.getId());
            return ResponseEntity.ok().body("L'article " + id_item + " a été supprimé du panier " + cart.getId());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id_cart}/supprimerArticle")
    public ResponseEntity<String> deleteAllItemsOfCart(@PathVariable("id_cart") final int id_cart){
        Optional<Cart> optCart = cartService.get(id_cart);
        if (optCart.isPresent()){
            Cart cart = optCart.get();
            cartService.deleteAllItemsOfCart(cart.getId());
            return ResponseEntity.ok().body("Le panier " + cart.getId() + " a été vidé.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id_cart}/ajouterArticle={id_item}")
    public ResponseEntity<String> addItemInCart(@PathVariable("id_cart") final int id_cart, @PathVariable("id_item") final int id_item){
        Optional<Cart> optCart = cartService.get(id_cart);
        if (optCart.isPresent()) {
            Cart cart = optCart.get();
            cartService.addItemOfCart(id_item, cart.getId());
            return ResponseEntity.ok().body("L'article " + id_item + " a été ajouté au panier " + cart.getId());
        }
        return ResponseEntity.notFound().build();
    }
}