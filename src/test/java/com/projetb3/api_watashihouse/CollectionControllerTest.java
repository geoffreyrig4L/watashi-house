package com.projetb3.api_watashihouse;

import com.projetb3.api_watashihouse.model.Collection;
import com.projetb3.api_watashihouse.repository.CollectionRepository;
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
public class CollectionControllerTest implements H2TestJpaConfig {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CollectionRepository collectionRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        Collection collection = new Collection();
        collection.setNom("WatSakura no hana");
        collectionRepository.save(collection);
        Collection collection2 = new Collection();
        collection2.setNom("Hoken");
        collectionRepository.save(collection2);
        Collection collection3 = new Collection();
        collection3.setNom("Collection 3");
        collectionRepository.save(collection3);
    }

    @Test
    void should_get_all_collections() throws Exception{
        mockMvc.perform(get("/collections?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nom",is("WatSakura no hana")))
                .andExpect(jsonPath("$.content[1].nom",is("Hoken")))
                .andExpect(jsonPath("$.content[2].nom",is("Collection 3")));
    }

    @Test
    void should_get_one_collection() throws Exception{
        mockMvc.perform(get("/collections/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("WatSakura no hana")));
    }

    @Test
    void should_not_get_one_collection() throws Exception{
        mockMvc.perform(get("/collections/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_collection() throws Exception{
        mockMvc.perform(put("/collections/2")
                        .content("{\"id_collection\":2,\"nom\":\"Collection 2\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/collections/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("Collection 2")));
    }

    @Test
    void should_not_put_one_collection() throws Exception{
        mockMvc.perform(put("/collections/50")
                        .content("{\"id_collection\":2,\"nom\":table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de collectionController
    }

    @Test
    void should_delete_one_collection() throws Exception{
        mockMvc.perform(delete("/collections/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_collection() throws Exception{
        mockMvc.perform(delete("/collections/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
