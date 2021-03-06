package com.projetb3.api.controller;

import com.projetb3.api.model.Cart;
import com.projetb3.api.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.projetb3.api.model.Cart.computePrice;
import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/paniers")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Cart>> getAll(@RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<Cart> cartsList = cartService.getAll();
            return ResponseEntity.ok(cartsList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> get(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Cart> cart = cartService.get(id);
        if (cart.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            return ResponseEntity.ok(cart.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/utilisateur={id}")
    public ResponseEntity<Cart> cartOfUser(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<Cart> cart = cartService.cartOfUser(id);
        if (cart.isPresent() && verifySenderOfRequest(token, Optional.of(cart.get().getUser().getId()))) {
            return ResponseEntity.ok(cart.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> delete(@PathVariable final int id, @RequestHeader("Authentication") final String token) {
        Optional<Cart> optCart = cartService.get(id);
        if (optCart.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            cartService.delete(id);
            return ResponseEntity.ok().body("Le panier a ??t?? supprim??e.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id_cart}/supprimerArticle={id_item}")
    public ResponseEntity<String> deleteItemOfCart(@PathVariable("id_cart") final int id_cart,
                                                   @PathVariable("id_item") final int id_item,
                                                   @RequestHeader("Authentication") final String token) {
        Optional<Cart> optCart = cartService.get(id_cart);
        if (optCart.isPresent() && verifySenderOfRequest(token, Optional.of(optCart.get().getUser().getId()))) {
            Cart cart = optCart.get();
            cartService.deleteItemOfCart(id_item, cart.getId());
            saveWithGoodPrice(cart);
            return ResponseEntity.ok().body("L'article " + id_item + " a ??t?? supprim?? du panier " + cart.getId());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id_cart}/supprimerArticle")
    public ResponseEntity<String> deleteAllItemsOfCart(@PathVariable("id_cart") final int id_cart,
                                                       @RequestHeader("Authentication") final String token) {
        Optional<Cart> optCart = cartService.get(id_cart);
        if (optCart.isPresent() && verifySenderOfRequest(token, Optional.of(optCart.get().getUser().getId()))) {
            Cart cart = optCart.get();
            cartService.deleteAllItemsOfCart(cart.getId());
            saveWithGoodPrice(cart);
            return ResponseEntity.ok().body("Le panier " + cart.getId() + " a ??t?? vid??.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id_cart}/ajouterArticle={id_item}")
    public ResponseEntity<String> addItemInCart(@PathVariable("id_cart") final int id_cart,
                                                @PathVariable("id_item") final int id_item,
                                                @RequestHeader("Authentication") final String token) {
        Optional<Cart> optCart = cartService.get(id_cart);
        if (optCart.isPresent() && verifySenderOfRequest(token, Optional.of(optCart.get().getUser().getId()))) {
            Cart cart = optCart.get();
            cartService.addItemOfCart(id_item, cart.getId());
            saveWithGoodPrice(cart);
            return ResponseEntity.ok().body("L'article " + id_item + " a ??t?? ajout?? au panier " + cart.getId());
        }
        return ResponseEntity.notFound().build();
    }

    private void saveWithGoodPrice(Cart cart) {
        cart.setPrice(computePrice(cart.getItems()));
        cartService.save(cart);
    }

}