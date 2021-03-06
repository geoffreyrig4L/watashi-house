package com.projetb3.api;

import com.projetb3.api.model.Order;
import com.projetb3.api.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public OrderRepository orderRepository;

    @BeforeEach
    void insertInH2(){
        saveOrderInH2("1234567890", 2000);
        saveOrderInH2("0987654321", 3000);
        saveOrderInH2("1114447770", 4000);
    }

    private void saveOrderInH2(String number, int prixTot) {
        Order order = new Order();
        order.setNumber(number);
        order.setDateOfPurchase(LocalDateTime.now());
        order.setTotalPrice(prixTot);
        orderRepository.save(order);
    }

    @Test
    void should_get_all_orders() throws Exception{
        mockMvc.perform(get("/commandes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].number",is("1234567890")))
                .andExpect(jsonPath("$.content[1].number",is("0987654321")))
                .andExpect(jsonPath("$.content[2].number",is("1114447770")));
    }

    @Test
    void should_get_one_order() throws Exception{
        mockMvc.perform(get("/commandes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number",is("1234567890")));
    }

    @Test
    void should_not_get_one_order() throws Exception{
        mockMvc.perform(get("/commandes/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_order() throws Exception{
        mockMvc.perform(put("/commandes/2")
                        .content("{\"id\":2,\"number\":\"22222222222222\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/commandes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number",is("22222222222222")));
    }

    @Test
    void should_not_put_one_order() throws Exception{
        mockMvc.perform(put("/commandes/50")
                        .content("{\"id\":2,\"number\":\"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_order() throws Exception{
        mockMvc.perform(delete("/commandes/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_order() throws Exception{
        mockMvc.perform(delete("/commandes/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
