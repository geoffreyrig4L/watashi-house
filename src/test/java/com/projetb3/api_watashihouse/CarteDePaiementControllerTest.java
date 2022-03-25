package com.projetb3.api_watashihouse;

import com.projetb3.api_watashihouse.model.CarteDePaiement;
import com.projetb3.api_watashihouse.repository.CarteDePaiementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //force le contexte spring boot a etre recharge apres ce test
//permet ainsi de ne pas creer de conflit apres une suppression   ELLE EST PRESENTE DANS LE H2TestJpaConfig DONT CETTE CLASSE HERITE
public class CarteDePaiementControllerTest implements H2TestJpaConfig {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CarteDePaiementRepository carteDePaiementRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        CarteDePaiement carteDePaiement = new CarteDePaiement();
        carteDePaiement.setNumero("4973556787121109");
        carteDePaiement.setCvc("659");
        carteDePaiement.setAnnee_expiration("25");
        carteDePaiement.setMois_expiration("12");
        carteDePaiementRepository.save(carteDePaiement);
        CarteDePaiement carteDePaiement2 = new CarteDePaiement();
        carteDePaiement2.setNumero("4973231467874433");
        carteDePaiement2.setCvc("098");
        carteDePaiement2.setAnnee_expiration("24");
        carteDePaiement2.setMois_expiration("10");
        carteDePaiementRepository.save(carteDePaiement2);
        CarteDePaiement carteDePaiement3 = new CarteDePaiement();
        carteDePaiement3.setNumero("4677088731314765");
        carteDePaiement3.setCvc("236");
        carteDePaiement3.setAnnee_expiration("23");
        carteDePaiement3.setMois_expiration("01");
        carteDePaiementRepository.save(carteDePaiement3);
    }

    @Test
    void should_get_all_carteDePaiements() throws Exception{
        mockMvc.perform(get("/cartes-de-paiement?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].numero",is("4973556787121109")))
                .andExpect(jsonPath("$.content[1].numero",is("4973231467874433")))
                .andExpect(jsonPath("$.content[2].numero",is("4677088731314765")));
    }

    @Test
    void should_get_one_carteDePaiement() throws Exception{
        mockMvc.perform(get("/cartes-de-paiement/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero",is("4973556787121109")));
    }

    @Test
    void should_not_get_one_carteDePaiement() throws Exception{
        mockMvc.perform(get("/cartes-de-paiement/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_carteDePaiement() throws Exception{
        mockMvc.perform(put("/cartes-de-paiement/2")
                        .content("{\"id_carteDePaiement\":2,\"numero\":\"1111111111111111\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/cartes-de-paiement/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero",is("1111111111111111")));
    }

    @Test
    void should_not_put_one_carteDePaiement() throws Exception{
        mockMvc.perform(put("/cartes-de-paiement/50")
                        .content("{\"id_carteDePaiement\":2,\"numero\":1111111111111111\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de carteDePaiementController
    }

    @Test
    void should_delete_one_carteDePaiement() throws Exception{
        mockMvc.perform(delete("/cartes-de-paiement/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_carteDePaiement() throws Exception{
        mockMvc.perform(delete("/cartes-de-paiement/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
