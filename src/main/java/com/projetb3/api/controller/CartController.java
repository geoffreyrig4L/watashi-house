package com.projetb3.api.controller;

import com.projetb3.api.model.Cart;
import com.projetb3.api.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCart(@PathVariable("id") final int id, @RequestBody Cart modified) {
        Optional<Cart> optCart = cartService.get(id);
        if (optCart.isPresent()) {
            Cart current = optCart.get();
            if(modified.getPrice() != 0) {
                current.setPrice(modified.getPrice());
            }
            current.getItems().clear();
            if(!modified.getItems().isEmpty()){
                current.setItems(modified.getItems());
            }
            cartService.save(current);
            return ResponseEntity.ok().body("Le panier " + current.getId() + " a été modifié.");
        }
        return ResponseEntity.notFound().build();
    }

}
