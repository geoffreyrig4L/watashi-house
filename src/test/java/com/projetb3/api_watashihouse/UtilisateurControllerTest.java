package com.projetb3.api_watashihouse;

import com.projetb3.api_watashihouse.model.Utilisateur;
import com.projetb3.api_watashihouse.repository.UtilisateurRepository;
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
public class UtilisateurControllerTest implements H2TestJpaConfig {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public UtilisateurRepository utilisateurRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setCivilite("Madame");
        utilisateur.setPrenom("Olivia");
        utilisateur.setNom("Hamer");
        utilisateur.setEmail("olivia.hamer@gmail.com");
        utilisateur.setMdp("ohamer");
        utilisateur.setTel("0601010101");
        utilisateur.setAdresse_livraison("31 rue de Victor Hugo 95210 Argenteuil");
        utilisateur.setAdresse_facturation("31 rue de Victor Hugo 95210 Argenteuil");
        utilisateur.setPays("France");
        utilisateurRepository.save(utilisateur);
        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setCivilite("Madame");
        utilisateur2.setPrenom("Talia");
        utilisateur2.setNom("Zhao");
        utilisateur2.setEmail("talia.zhao@gmail.com");
        utilisateur2.setMdp("tzao");
        utilisateur2.setTel("0602020202");
        utilisateur2.setAdresse_livraison("42 avenue du général de Gaulle 93421 Pantin");
        utilisateur2.setAdresse_facturation("42 avenue du général de Gaulle 93421 Pantin");
        utilisateur2.setPays("France");
        utilisateurRepository.save(utilisateur2);
        Utilisateur utilisateur10 = new Utilisateur();
        utilisateur10.setCivilite("Monsieur");
        utilisateur10.setPrenom("Helio");
        utilisateur10.setNom("Pinto");
        utilisateur10.setEmail("helio.pinto@gmail.com");
        utilisateur10.setMdp("hpinto");
        utilisateur10.setTel("0603030303");
        utilisateur10.setAdresse_livraison("45 rue de Marie Curie 77231 Meaux");
        utilisateur10.setAdresse_facturation("45 rue de Marie Curie 77231 Meaux");
        utilisateur10.setPays("France");
        utilisateurRepository.save(utilisateur10);
    }

    @Test
    void should_get_all_utilisateurs() throws Exception{
        mockMvc.perform(get("/utilisateurs?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nom",is("Hamer")))
                .andExpect(jsonPath("$.content[1].nom",is("Zhao")))
                .andExpect(jsonPath("$.content[2].nom",is("Pinto")));
    }

    @Test
    void should_get_one_utilisateur() throws Exception{
        mockMvc.perform(get("/utilisateurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("Hamer")));
    }

    @Test
    void should_not_get_one_utilisateur() throws Exception{
        mockMvc.perform(get("/utilisateurs/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_utilisateur() throws Exception{
        mockMvc.perform(put("/utilisateurs/2")
                        .content("{\"id_utilisateur\":2,\"prenom\":\"Olivier\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/utilisateurs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom",is("Olivier")));
    }

    @Test
    void should_not_put_one_utilisateur() throws Exception{
        mockMvc.perform(put("/utilisateurs/50")
                        .content("{\"id_utilisateur\":2,\"nom\":\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de utilisateurController
    }

    @Test
    void should_delete_one_utilisateur() throws Exception{
        mockMvc.perform(delete("/utilisateurs/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_utilisateur() throws Exception{
        mockMvc.perform(delete("/utilisateurs/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
