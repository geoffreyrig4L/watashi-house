package com.projetb3.api;

import com.projetb3.api.model.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public UserRepository userRepository;

    @BeforeEach
    void insertInH2() {
        saveUserInH2(0, "Madame", "Olivia", "Hamer", "olivia.hamer@gmail.com", "ohamer", "0601010101", "31 rue de Victor Hugo", "95210", "Argenteuil", "France", "client");
        saveUserInH2(1, "Madame", "Talia", "Zhao", "talia.zhao@gmail.com", "tzao", "0602020202", "42 avenue du général de Gaulle", "93421", "Pantin", "France", "client");
        saveUserInH2(2, "Monsieur", "Helio", "Pinto", "helio.pinto@gmail.com", "hpinto", "0603030303", "45 rue de Marie Curie", "77231", "Meaux", "France", "client");
    }

    private User saveUserInH2(int id, String gender, String firstname, String lastname, String email, String password, String phone,
                              String address, String zipCode, String city, String country, String typeUser) {
        User user = new User();
        user.setId(id);
        user.setGender(gender);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setHash(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setZipCode(zipCode);
        user.setCity(city);
        user.setCountry(country);
        user.setTypeUser(typeUser);
        userRepository.save(user);
        return user;
    }

    @Test
    void should_get_all_utilisateurs() throws Exception {
        mockMvc.perform(get("/utilisateurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].lastname", is("Hamer")))
                .andExpect(jsonPath("$.content[1].lastname", is("Zhao")))
                .andExpect(jsonPath("$.content[2].lastname", is("Pinto")));
    }

    @Test
    void should_get_one_utilisateur() throws Exception {
        mockMvc.perform(get("/utilisateurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", is("Zhao")));
    }

    @Test
    void should_not_get_one_utilisateur() throws Exception {
        mockMvc.perform(get("/utilisateurs/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_utilisateur() throws Exception {
        mockMvc.perform(put("/utilisateurs/2")
                        .content("{\"id\":2,\"lastname\":\"Olivier\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/utilisateurs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname",is("Olivier")));
    }

    @Test
    void should_not_put_one_utilisateur() throws Exception {
        mockMvc.perform(put("/utilisateurs/50")
                        .content("{\"id\":2,\"lastname\":\"Pinto\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_utilisateur() throws Exception {
        mockMvc.perform(delete("/utilisateurs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_utilisateur() throws Exception {
        mockMvc.perform(delete("/utilisateurs/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
