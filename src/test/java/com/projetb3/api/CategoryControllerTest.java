package com.projetb3.api;

import com.projetb3.api.model.Category;
import com.projetb3.api.repository.CategoryRepository;
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
class CategoryControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CategoryRepository categoryRepository;

    @BeforeEach
    void insertInH2(){
        saveCategoryInH2( "Meuble");
        saveCategoryInH2("Decoration");
        saveCategoryInH2("Luminaire");
    }

    private void saveCategoryInH2(String nom) {
        Category category = new Category();
        category.setName(nom);
        categoryRepository.save(category);
    }

    @Test
    void should_get_all_categories() throws Exception{
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name",is("Meuble")));
    }

    @Test
    void should_get_one_category() throws Exception{
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Meuble")));
    }

    @Test
    void should_not_get_one_category() throws Exception{
        mockMvc.perform(get("/categories/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_category() throws Exception{
        mockMvc.perform(put("/categories/2")
                        .content("{\"id\":2,\"name\":\"canapé\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("canapé")));
    }

    @Test
    void should_not_put_one_category() throws Exception{
        mockMvc.perform(put("/categories/50")
                        .content("{\"id\":2,\"name\":table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_category() throws Exception{
        mockMvc.perform(delete("/categories/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_category() throws Exception{
        mockMvc.perform(delete("/categories/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCategoriesOfRoom() {
    }
}
