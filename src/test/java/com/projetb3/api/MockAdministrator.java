package com.projetb3.api;

import com.projetb3.api.model.Cart;
import com.projetb3.api.model.Favorite;
import com.projetb3.api.model.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockAdministrator {

    public static User mockAdministrator() {
        User user = mock(User.class);
        Cart cart = mock(Cart.class);
        Favorite favorite = mock(Favorite.class);
        when(user.getId()).thenReturn(1);
        when(user.getFirstname()).thenReturn("Thomas");
        when(user.getLastname()).thenReturn("Mr. Anderson");
        when(user.getEmail()).thenReturn("neo.anderson@gmail.com");
        mockCartAndFavorite(user, cart, favorite);
        when(user.getTypeUser()).thenReturn("administrateur");
        return user;
    }

    private static void mockCartAndFavorite(User user, Cart cart, Favorite favorite) {
        when(user.getCart()).thenReturn(cart);
        when(user.getFavorite()).thenReturn(favorite);
        when(cart.getId()).thenReturn(1);
        when(favorite.getId()).thenReturn(1);
    }
}
