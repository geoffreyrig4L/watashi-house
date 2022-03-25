package com.projetb3.api_watashihouse;

import com.projetb3.api_watashihouse.model.Categorie;
import com.projetb3.api_watashihouse.repository.CategorieRepository;
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
public class CategorieControllerTest implements H2TestJpaConfig {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CategorieRepository categorieRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        Categorie categorie = new Categorie();
        categorie.setNom("table");
        categorieRepository.save(categorie);
        Categorie categorie2 = new Categorie();
        categorie2.setNom("chaise");
        categorieRepository.save(categorie2);
        Categorie categorie3 = new Categorie();
        categorie3.setNom("bureau");
        categorieRepository.save(categorie3);
    }

    @Test
    void should_get_all_categories() throws Exception{
        mockMvc.perform(get("/categories?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nom",is("table")))
                .andExpect(jsonPath("$.content[1].nom",is("chaise")))
                .andExpect(jsonPath("$.content[2].nom",is("bureau")));
    }

    @Test
    void should_get_one_categorie() throws Exception{
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("table")));
    }

    @Test
    void should_not_get_one_categorie() throws Exception{
        mockMvc.perform(get("/categories/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_categorie() throws Exception{
        mockMvc.perform(put("/categories/2")
                        .content("{\"id_categorie\":2,\"nom\":\"canapé\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("canapé")));
    }

    @Test
    void should_not_put_one_categorie() throws Exception{
        mockMvc.perform(put("/categories/50")
                        .content("{\"id_categorie\":2,\"nom\":table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de categorieController
    }

    @Test
    void should_delete_one_categorie() throws Exception{
        mockMvc.perform(delete("/categories/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_categorie() throws Exception{
        mockMvc.perform(delete("/categories/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
