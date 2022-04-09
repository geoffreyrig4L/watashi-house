package com.projetb3.api_watashihouse;

import com.projetb3.api_watashihouse.model.CarteDePaiement;
import com.projetb3.api_watashihouse.model.Commande;
import com.projetb3.api_watashihouse.model.Utilisateur;
import com.projetb3.api_watashihouse.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
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
//
//    @BeforeEach  // s execute avant chaque methode de test
//    void insertInH2(){
//        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
//        saveUtilisateurInH2(1,"Madame", "Olivia", "Hamer", "olivia.hamer@gmail.com", "ohamer", "0601010101", "31 rue de Victor Hugo 95210 Argenteuil", "31 rue de Victor Hugo 95210 Argenteuil", "France");
//        saveUtilisateurInH2(2,"Madame", "Talia", "Zhao", "talia.zhao@gmail.com", "tzao", "0602020202", "42 avenue du général de Gaulle 93421 Pantin", "42 avenue du général de Gaulle 93421 Pantin", "France");
//        saveUtilisateurInH2(3,"Monsieur", "Helio", "Pinto", "helio.pinto@gmail.com", "hpinto", "0603030303", "45 rue de Marie Curie 77231 Meaux", "45 rue de Marie Curie 77231 Meaux", "France");
//    }
//
//    private void saveUtilisateurInH2(int id, String civilite, String prenom, String nom, String email, String mdp, String telephone, String adresseLivraison, String adresseFacturation, String pays) {
//        Utilisateur utilisateur = new Utilisateur();
//        utilisateur.setId(id);
//        utilisateur.setCivilite(civilite);
//        utilisateur.setPrenom(prenom);
//        utilisateur.setNom(nom);
//        utilisateur.setEmail(email);
//        utilisateur.setMdp(mdp);
//        utilisateur.setTel(telephone);
//        utilisateur.setAdresse_livraison(adresseLivraison);
//        utilisateur.setAdresse_facturation(adresseFacturation);
//        utilisateur.setPays(pays);
//        utilisateur.setCarteDePaiements(List.of(mock(CarteDePaiement.class)));
//        utilisateur.setCommandes(List.of(mock(Commande.class)));
//        utilisateurRepository.save(utilisateur);
//    }

    @BeforeEach  // s execute avant chaque methode de test
    void insertInH2(){
        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
        Utilisateur utilisateur1 = saveUtilisateurInH2(1,"Madame", "Olivia", "Hamer", "olivia.hamer@gmail.com", "ohamer", "0601010101", "31 rue de Victor Hugo 95210 Argenteuil", "31 rue de Victor Hugo 95210 Argenteuil", "France");
        Utilisateur utilisateur2 = saveUtilisateurInH2(2,"Madame", "Talia", "Zhao", "talia.zhao@gmail.com", "tzao", "0602020202", "42 avenue du général de Gaulle 93421 Pantin", "42 avenue du général de Gaulle 93421 Pantin", "France");
        Utilisateur utilisateur3 = saveUtilisateurInH2(3,"Monsieur", "Helio", "Pinto", "helio.pinto@gmail.com", "hpinto", "0603030303", "45 rue de Marie Curie 77231 Meaux", "45 rue de Marie Curie 77231 Meaux", "France");
        utilisateur1.setCarteDePaiements(List.of(new CarteDePaiement("24356","474","12","02",utilisateur1)));
        utilisateur2.setCarteDePaiements(List.of(new CarteDePaiement("24356","474","12","02",utilisateur2)));
        utilisateur3.setCarteDePaiements(List.of(new CarteDePaiement("24356","474","12","02",utilisateur3)));
       for(Utilisateur utilisateur : List.of(utilisateur1,utilisateur2,utilisateur3)) {
           utilisateurRepository.save(utilisateur);
       }
    }

    private Utilisateur saveUtilisateurInH2(int id, String civilite, String prenom, String nom, String email, String mdp, String telephone, String adresseLivraison, String adresseFacturation, String pays) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        utilisateur.setCivilite(civilite);
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setEmail(email);
        utilisateur.setMdp(mdp);
        utilisateur.setTel(telephone);
        utilisateur.setAdresse_livraison(adresseLivraison);
        utilisateur.setAdresse_facturation(adresseFacturation);
        utilisateur.setPays(pays);
        System.out.println(utilisateur.getId());
        return utilisateur;
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

    /*@Test
    void should_put_one_utilisateur() throws Exception{
        mockMvc.perform(put("/utilisateurs/2")
                        .content("{\"id_utilisateur\":2,\"prenom\":\"Olivier\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }*/

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
