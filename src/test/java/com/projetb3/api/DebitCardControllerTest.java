package com.projetb3.api;

import com.projetb3.api.model.DebitCard;
import com.projetb3.api.model.User;
import com.projetb3.api.repository.DebitCardRepository;
import com.projetb3.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DebitCardControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public DebitCardRepository debitCardRepository;

    @Autowired
    public UserRepository userRepository;

    @BeforeEach
    void insertInH2(){
        saveCarteInH2("4973556787121109","659","25","12");
        saveCarteInH2("4973231467874433","098","24","10");
        saveCarteInH2("4677088731314765","236","23","01");
    }

    private void saveCarteInH2(String number, String cvc, String year, String month) {
        var user = mock(User.class);
        DebitCard debitCard = new DebitCard();
        debitCard.setNumber(number);
        debitCard.setCvc(cvc);
        debitCard.setExpiryYear(year);
        debitCard.setExpiryMonth(month);
        debitCard.setUser(user);
        debitCardRepository.save(debitCard);
    }

    @Test
    void should_get_all_cards() throws Exception{
        mockMvc.perform(get("/cartes-de-paiement"))
                .andExpect(jsonPath("$.content[0].number",is("4973556787121109")))
                .andExpect(jsonPath("$.content[1].number",is("4973231467874433")))
                .andExpect(jsonPath("$.content[2].number",is("4677088731314765")));
    }

    @Test
    void should_get_one_card() throws Exception{
        mockMvc.perform(get("/cartes-de-paiement/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number",is("4973556787121109")));
    }

    @Test
    void should_not_get_one_card() throws Exception{
        mockMvc.perform(get("/cartes-de-paiement/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_card() throws Exception{
        mockMvc.perform(put("/cartes-de-paiement/2")
                        .content("{\"id\":2,\"number\":\"1111111111111111\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/cartes-de-paiement/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number",is("1111111111111111")));
    }

    @Test
    void should_not_put_one_card() throws Exception{
        mockMvc.perform(put("/cartes-de-paiement/50")
                        .content("{\"id\":2,\"number\":1111111111111111\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_card() throws Exception{
        mockMvc.perform(delete("/cartes-de-paiement/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_card() throws Exception{
        mockMvc.perform(delete("/cartes-de-paiement/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
