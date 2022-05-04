package com.projetb3.api;

import com.projetb3.api.model.Order;
import com.projetb3.api.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //force le contexte spring boot a etre recharge apres ce test
//permet ainsi de ne pas creer de conflit apres une suppression   ELLE EST PRESENTE DANS LE H2TestJpaConfig DONT CETTE CLASSE HERITE
public class OrderControllerTest implements H2TestJpaConfig {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public OrderRepository orderRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        saveCommandeInH2("1234567890", 2000);
        saveCommandeInH2("0987654321", 3000);
        saveCommandeInH2("1114447770", 4000);
    }

    private void saveCommandeInH2(String numero, int prixTot) {
        Order order = new Order();
        order.setNumber(numero);
        order.setDateOfPurchase(LocalDateTime.now());
        order.setTotalPrice(prixTot);
        orderRepository.save(order);
    }

    @Test
    void should_get_all_commandes() throws Exception{
        mockMvc.perform(get("/commandes?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].numero",is("1234567890")))
                .andExpect(jsonPath("$.content[1].numero",is("0987654321")))
                .andExpect(jsonPath("$.content[2].numero",is("1114447770")));
    }

    @Test
    void should_get_one_commande() throws Exception{
        mockMvc.perform(get("/commandes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero",is("1234567890")));
    }

    @Test
    void should_not_get_one_commande() throws Exception{
        mockMvc.perform(get("/commandes/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_commande() throws Exception{
        mockMvc.perform(put("/commandes/2")
                        .content("{\"id_commande\":2,\"date_achat\":\"31/11/2021 13:45:23\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/commandes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date_achat",is("31/11/2021 13:45:23")));
    }

    @Test
    void should_not_put_one_commande() throws Exception{
        mockMvc.perform(put("/commandes/50")
                        .content("{\"id_commande\":2,\"numero\":\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de commandeController
    }

    @Test
    void should_delete_one_commande() throws Exception{
        mockMvc.perform(delete("/commandes/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_commande() throws Exception{
        mockMvc.perform(delete("/commandes/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
