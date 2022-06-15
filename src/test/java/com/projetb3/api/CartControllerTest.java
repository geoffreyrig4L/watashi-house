package com.projetb3.api;

import com.projetb3.api.model.Cart;
import com.projetb3.api.model.User;
import com.projetb3.api.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CartControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CartRepository categoryRepository;

    @BeforeEach
    void insertInH2(){
        saveCartInH2();
        saveCartInH2();
        saveCartInH2();
    }

    private void saveCartInH2() {
        Cart cart = new Cart();
        cart.setPrice(0);
        cart.setItems(new ArrayList<>());
        categoryRepository.save(cart);
    }

    @Test
    void should_get_all_carts() throws Exception{
        mockMvc.perform(get("/paniers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id",is(1)));
    }

    @Test
    void should_get_one_cart() throws Exception{
        mockMvc.perform(get("/paniers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    void should_not_get_one_cart() throws Exception{
        mockMvc.perform(get("/paniers/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_one_cart() throws Exception{
        mockMvc.perform(delete("/paniers/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_cart() throws Exception{
        mockMvc.perform(delete("/paniers/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void cartOfUser() {
    }

    @Test
    void deleteItemOfCart() {
    }

    @Test
    void deleteAllItemsOfCart() {
    }

    @Test
    void addItemInCart() {
    }
}