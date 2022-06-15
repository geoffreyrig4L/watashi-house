package com.projetb3.api;

import com.projetb3.api.model.Item;
import com.projetb3.api.repository.ItemRepository;
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
class ItemControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ItemRepository itemRepository;

    @BeforeEach
    void insertInH2(){
        saveArticleInH2("chaise","une magnifique chaise","img1", "img2","rouge",2999);
        saveArticleInH2("table","une magnifique table","img3","img4","marron",6999);
        saveArticleInH2("bureau","une magnifique bureau","img5","img6","chêne",9999);
    }

    public void saveArticleInH2(String name, String description, String image1, String image2, String color, int price){
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setImage1(image1);
        item.setImage2(image2);
        item.setColor(color);
        item.setPrice(price);
        item.setStock(100);
        itemRepository.save(item);
    }

    @Test
    void should_get_all_articles() throws Exception{
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name",is("chaise")))
                .andExpect(jsonPath("$.content[1].name",is("table")))
                .andExpect(jsonPath("$.content[2].name",is("bureau")));
    }

    @Test
    void should_get_one_article() throws Exception{
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("chaise")));
    }

    @Test
    void should_not_get_one_article() throws Exception{
        mockMvc.perform(get("/articles/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_article() throws Exception{
        mockMvc.perform(put("/articles/2")
                        .content("{\"id\":2,\"name\":\"table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/articles/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("table de chevet")));
    }

    @Test
    void should_not_put_one_article() throws Exception{
        mockMvc.perform(put("/articles/50")
                        .content("{\"id\":2,\"name\":table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_article() throws Exception{
        mockMvc.perform(delete("/articles/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_article() throws Exception{
        mockMvc.perform(delete("/articles/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
