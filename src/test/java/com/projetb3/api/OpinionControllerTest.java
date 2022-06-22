package com.projetb3.api;

import com.projetb3.api.model.Opinion;
import com.projetb3.api.model.User;
import com.projetb3.api.repository.OpinionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OpinionControllerTest implements H2TestJpaConfig {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public OpinionRepository opinionRepository;

    @BeforeEach
    void insertInH2(){
        saveOpinionInH2("Ce canape est vraiment fantastique", 5.0F);
        saveOpinionInH2("Ce canape est vraiment cool", 3.1F);
        saveOpinionInH2("Ce canape est vraiment a chié", 1.4F);
    }

    private void saveOpinionInH2(String comment, Float note) {
        Opinion opinion = new Opinion();
        opinion.setComment(comment);
        opinion.setNote(note);
        opinion.setDateOfPublication(LocalDateTime.now());
        opinionRepository.save(opinion);
    }

    @Test
    void should_get_all_opinions() throws Exception{
        mockMvc.perform(get("/avis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].comment",is("Ce canape est vraiment fantastique")));
    }

    @Test
    void should_get_one_opinion() throws Exception{
        mockMvc.perform(get("/avis/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment",is("Ce canape est vraiment cool")));
    }

    @Test
    void should_not_get_one_opinion() throws Exception{
        mockMvc.perform(get("/avis/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_opinion() throws Exception{
        mockMvc.perform(put("/avis/2")
                        .content("{\"name\":\"Ce canape est vraiment TROP à chié\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/avis/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment",is("Ce canape est vraiment TROP à chié")));
    }

    @Test
    void should_not_put_one_opinion() throws Exception{
        mockMvc.perform(put("/avis/50")
                        .content("\"name\":Ce canape est vraiment TROP à chié\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_one_opinion() throws Exception{
        mockMvc.perform(delete("/avis/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_one_opinion() throws Exception{
        mockMvc.perform(delete("/avis/50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getOpinionsOfItem() {
    }

    @Test
    void getAverageOfItem() {
    }
}