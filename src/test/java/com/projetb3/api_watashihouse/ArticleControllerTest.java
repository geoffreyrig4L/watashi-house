package com.projetb3.api_watashihouse;

import com.projetb3.api_watashihouse.model.Article;
import com.projetb3.api_watashihouse.repository.ArticleRepository;
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
public class ArticleControllerTest implements H2TestJpaConfig {

    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ArticleRepository articleRepository;

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        Article article = new Article();
        article.setNom("chaise");
        article.setDescription("une magnifique chaise");
        article.setImages("images/chaise.png");
        article.setCouleur("rouge");
        article.setPrix(2999);
        article.setNb_avis(75);
        article.setNote(42);
        article.setStock(45);
        articleRepository.save(article);
        Article article2 = new Article();
        article2.setNom("table");
        article2.setDescription("une magnifique table");
        article2.setImages("images/table.png");
        article2.setCouleur("marron");
        article2.setPrix(6999);
        article2.setNb_avis(100);
        article2.setNote(44);
        article2.setStock(26);
        articleRepository.save(article2);
        Article article3 = new Article();
        article3.setNom("bureau");
        article3.setDescription("une magnifique bureau");
        article3.setImages("images/bureau.png");
        article3.setCouleur("chêne");
        article3.setPrix(9999);
        article3.setNb_avis(92);
        article3.setNote(39);
        article3.setStock(39);
        articleRepository.save(article3);
    }

    @Test
    void should_get_all_articles() throws Exception{
        mockMvc.perform(get("/articles?page=0&sortBy=id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nom",is("chaise")))
                .andExpect(jsonPath("$.content[1].nom",is("table")))
                .andExpect(jsonPath("$.content[2].nom",is("bureau")));
    }

    @Test
    void should_get_one_article() throws Exception{
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("chaise")));
    }

    @Test
    void should_not_get_one_article() throws Exception{
        mockMvc.perform(get("/articles/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_one_article() throws Exception{
        mockMvc.perform(put("/articles/2")
                        .content("{\"id_article\":2,\"nom\":\"table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/articles/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is("table de chevet")));
    }

    @Test
    void should_not_put_one_article() throws Exception{
        mockMvc.perform(put("/articles/50")
                        .content("{\"id_article\":2,\"nom\":table de chevet\"}")
                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de articleController
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
                .andExpect(status().isBadRequest());
    }
}
