package com.projetb3.api;

import com.projetb3.api.model.Cart;
import com.projetb3.api.model.Favorite;
import com.projetb3.api.model.Item;
import com.projetb3.api.model.User;
import com.projetb3.api.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.projetb3.api.MockAdministrator.mockAdministrator;
import static com.projetb3.api.security.AuthenticationWithJWT.create;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    private final

    @BeforeEach
    void insertInH2() {
        saveArticleInH2("chaise", "une magnifique chaise", "img1", "img2", "rouge", 2999);
        saveArticleInH2("table", "une magnifique table", "img3", "img4", "violet", 6999);
        saveArticleInH2("chaise 2", "une magnifique chaise", "img4", "img5", "rouge", 4999);
        saveArticleInH2("table 2", "une magnifique table", "img6", "img7", "marron", 5999);
        saveArticleInH2("bureau", "une magnifique bureau", "img8", "img9", "chêne", 9999);
    }

    public void saveArticleInH2(String name, String description, String image1, String image2, String color,
                                int price) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setImage1(image1);
        item.setImage2(image2);
        item.setColor(color);
        item.setPrice(price);
        item.setStock(100);
        item.setNote(null);
        itemRepository.save(item);
    }

    @Test
    void should_get_all_articles() throws Exception {
        mockMvc.perform(get("/articles?page=0&sortBy=name&orderBy=ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("bureau")))
                .andExpect(jsonPath("$.content[1].name", is("chaise")))
                .andExpect(jsonPath("$.content[2].name", is("chaise 2")));
    }

    @Test
    void should_get_items_only_red() throws Exception {
        mockMvc.perform(get("/articles/couleur=rouge?page=0&sortBy=id_article&orderBy=DESC"))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$.content[0].name", is("chaise 2")),
                        jsonPath("$.content[1].name", is("chaise")));
    }

    /*@Test
    void should_get_items_between_two_prices() throws Exception {
        mockMvc.perform(get("/articles/prixMin=3000/prixMax=7000?page=0&sortBy=nom&orderBy=ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[2].name", is("table 2")))
                .andExpect(jsonPath("$.content[0].name", is("chaise 2")))
                .andExpect(jsonPath("$.content[1].name", is("table")));
    }*/

    @Test
    void should_get_one_article() throws Exception {
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("chaise")));
    }

    @Test
    void should_not_get_one_article() throws Exception {
        mockMvc.perform(get("/articles/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_article() throws Exception {
        mockMvc.perform(put("/articles/2")
                        .content("{\"name\":\"table de chevet\"}")
                        .header("Authentication", create(mockAdministrator()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/articles/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("table de chevet")));
    }

    @Test
    void should_not_put_one_article() throws Exception {
        mockMvc.perform(put("/articles/50")
                        .content("{\"id\":2,\"name\":table de chevet\"}")
                        .header("Authentication", create(mockAdministrator()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_article() throws Exception {
        mockMvc.perform(delete("/articles/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authentication", create(mockAdministrator()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/articles/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_not_delete_one_article() throws Exception {
        mockMvc.perform(delete("/articles/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
