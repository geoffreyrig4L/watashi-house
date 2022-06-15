package com.projetb3.api;

import com.projetb3.api.model.SubCategory;
import com.projetb3.api.repository.SubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SubCategoryControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public SubCategoryRepository subCategoryRepository;

    @BeforeEach
    void insertInH2(){
        saveSubCategoryInH2("table");
        saveSubCategoryInH2("chaise");
        saveSubCategoryInH2("bureau");
    }

    private void saveSubCategoryInH2(String nom) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(nom);
        subCategoryRepository.save(subCategory);
    }

    @Test
    void should_get_all_subCategories() throws Exception{
        mockMvc.perform(get("/sous-categories"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name",is("table")));
    }

    @Test
    void should_get_one_subCategory() throws Exception{
        mockMvc.perform(get("/sous-categories/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("table")));
    }

    @Test
    void should_not_get_one_subCategory() throws Exception{
        mockMvc.perform(get("/sous-categories/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_subCategory() throws Exception{
        mockMvc.perform(put("/sous-categories/2")
                        .content("{\"id\":2,\"name\":\"canapé\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/sous-categories/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("canapé")));
    }

    @Test
    void should_not_put_one_subCategory() throws Exception{
        mockMvc.perform(put("/sous-categories/50")
                        .content("{\"id\":2,\"name\":table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_subCategory() throws Exception{
        mockMvc.perform(delete("/sous-categories/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_subCategory() throws Exception{
        mockMvc.perform(delete("/sous-categories/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSubCategoriesOfSubCategory() {
    }

    @Test
    void getSubCategoriesOfRoom() {
    }
}