package com.projetb3.api.service;

import com.projetb3.api.model.Cart;
import com.projetb3.api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Iterable<Cart> getAll(){
        return cartRepository.findAll();
    }

    public Optional<Cart> cartOfUser(int id) {
        return cartRepository.cartOfUser(id);
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public Optional<Cart> get(int id) {
        return cartRepository.findById(id);
    }

    public void deleteItemOfCart(int id_item, int id_cart) {
        cartRepository.deleteItemOfCart(id_item, id_cart);
    }

    public void deleteAllItemsOfCart(int id) {
        cartRepository.deleteAllItemsOfCart(id);
    }

    public void addItemOfCart(int id_item, int id_cart) {
        cartRepository.addItemInCart(id_item, id_cart);
    }
}
