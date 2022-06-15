package com.projetb3.api;

import com.projetb3.api.model.Collection;
import com.projetb3.api.repository.CollectionRepository;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // force le contexte spring boot a etre recharge apres ce test, permet ainsi de ne pas creer de conflit apres une suppression   ELLE EST PRESENTE DANS LE H2TestJpaConfig DONT CETTE CLASSE HERITE
class CollectionControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CollectionRepository collectionRepository;

    @BeforeEach
    void insertInH2(){
        saveCollectionInH2("WatSakura no hana");
        saveCollectionInH2("Hoken");
        saveCollectionInH2("Collection 3");
    }

    private void saveCollectionInH2(String name) {
        Collection collection = new Collection();
        collection.setName(name);
        collectionRepository.save(collection);
    }

    @Test
    void should_get_all_collections() throws Exception{
        mockMvc.perform(get("/collections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name",is("WatSakura no hana")))
                .andExpect(jsonPath("$.content[1].name",is("Hoken")))
                .andExpect(jsonPath("$.content[2].name",is("Collection 3")));
    }

    @Test
    void should_get_one_collection() throws Exception{
        mockMvc.perform(get("/collections/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("WatSakura no hana")));
    }

    @Test
    void should_not_get_one_collection() throws Exception{
        mockMvc.perform(get("/collections/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_collection() throws Exception{
        mockMvc.perform(put("/collections/2")
                        .content("{\"id\":2,\"name\":\"Collection 2\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/collections/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Collection 2")));
    }

    @Test
    void should_not_put_one_collection() throws Exception{
        mockMvc.perform(put("/collections/50")
                        .content("{\"id\":2,\"name\":table de chevet\"}")
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
                .andExpect(status().isNotFound());
    }
}
