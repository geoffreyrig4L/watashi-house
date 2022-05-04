//package com.projetb3.api;
//
//import com.projetb3.api.model.DebitCard;
//import com.projetb3.api.model.User;
//import com.projetb3.api.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
////@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //force le contexte spring boot a etre recharge apres ce test
////permet ainsi de ne pas creer de conflit apres une suppression   ELLE EST PRESENTE DANS LE H2TestJpaConfig DONT CETTE CLASSE HERITE
//public class UserControllerTest implements H2TestJpaConfig {
//
//    //pour que les tests fonctionnent : il faut que le getAll verifie les titres de tous sauf du dernier et que le delete supprime le dernier
//
//    @Autowired
//    public MockMvc mockMvc;
//
//    @Autowired
//    public UserRepository utilisateurRepository;
////
////    @BeforeEach  // s execute avant chaque methode de test
////    void insertInH2(){
////        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
////        saveUtilisateurInH2(1,"Madame", "Olivia", "Hamer", "olivia.hamer@gmail.com", "ohamer", "0601010101", "31 rue de Victor Hugo 95210 Argenteuil", "31 rue de Victor Hugo 95210 Argenteuil", "France");
////        saveUtilisateurInH2(2,"Madame", "Talia", "Zhao", "talia.zhao@gmail.com", "tzao", "0602020202", "42 avenue du général de Gaulle 93421 Pantin", "42 avenue du général de Gaulle 93421 Pantin", "France");
////        saveUtilisateurInH2(3,"Monsieur", "Helio", "Pinto", "helio.pinto@gmail.com", "hpinto", "0603030303", "45 rue de Marie Curie 77231 Meaux", "45 rue de Marie Curie 77231 Meaux", "France");
////    }
////
////    private void saveUtilisateurInH2(int id, String civilite, String prenom, String nom, String email, String mdp, String telephone, String adresseLivraison, String adresseFacturation, String pays) {
////        User utilisateur = new User();
////        utilisateur.setId(id);
////        utilisateur.setCivilite(civilite);
////        utilisateur.setPrenom(prenom);
////        utilisateur.setNom(nom);
////        utilisateur.setEmail(email);
////        utilisateur.setMdp(mdp);
////        utilisateur.setTel(telephone);
////        utilisateur.setAdresse_livraison(adresseLivraison);
////        utilisateur.setAdresse_facturation(adresseFacturation);
////        utilisateur.setPays(pays);
////        utilisateur.setDebitCards(List.of(mock(DebitCard.class)));
////        utilisateur.setOrders(List.of(mock(Order.class)));
////        utilisateurRepository.save(utilisateur);
////    }
//
//    @BeforeEach  // s execute avant chaque methode de test
//    void insertInH2(){
//        //les id sont generes automatiquements meme si on les modifies avec @GeneratedValue
//        User user1 = saveUtilisateurInH2(1,"Madame", "Olivia", "Hamer", "olivia.hamer@gmail.com", "ohamer", "0601010101", "31 rue de Victor Hugo 95210 Argenteuil", "France");
//        User user2 = saveUtilisateurInH2(2,"Madame", "Talia", "Zhao", "talia.zhao@gmail.com", "tzao", "0602020202", "42 avenue du général de Gaulle 93421 Pantin", "France");
//        User user3 = saveUtilisateurInH2(3,"Monsieur", "Helio", "Pinto", "helio.pinto@gmail.com", "hpinto", "0603030303", "45 rue de Marie Curie 77231 Meaux", "France");
//        user1.setDebitCards(List.of(new DebitCard("24356","474","12","02", user1)));
//        user2.setDebitCards(List.of(new DebitCard("24356","474","12","02", user2)));
//        user3.setDebitCards(List.of(new DebitCard("24356","474","12","02", user3)));
//       for(User user : List.of(user1, user2, user3)) {
//           utilisateurRepository.save(user);
//       }
//    }
//
//    private User saveUtilisateurInH2(int id, String civilite, String prenom, String nom, String email, String mdp, String telephone, String adresse, String pays) {
//        User user = new User();
//        user.setId(id);
//        user.setGender(civilite);
//        user.setFirstname(prenom);
//        user.setLastname(nom);
//        user.setEmail(email);
//        user.setPassword(mdp);
//        user.setPhone(telephone);
//        user.setAddress(adresse);
//        user.setCountry(pays);
//        return user;
//    }
//
//    @Test
//    void should_get_all_utilisateurs() throws Exception{
//        mockMvc.perform(get("/utilisateurs?page=0&sortBy=id"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].nom",is("Hamer")))
//                .andExpect(jsonPath("$.content[1].nom",is("Zhao")))
//                .andExpect(jsonPath("$.content[2].nom",is("Pinto")));
//    }
//
//    @Test
//    void should_get_one_utilisateur() throws Exception{
//        mockMvc.perform(get("/utilisateurs/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nom",is("Hamer")));
//    }
//
//    @Test
//    void should_not_get_one_utilisateur() throws Exception{
//        mockMvc.perform(get("/utilisateurs/50"))
//                .andExpect(status().isNotFound());
//    }
//
//    /*@Test
//    void should_put_one_utilisateur() throws Exception{
//        mockMvc.perform(put("/utilisateurs/2")
//                        .content("{\"id_utilisateur\":2,\"prenom\":\"Olivier\"}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }*/
//
//    @Test
//    void should_not_put_one_utilisateur() throws Exception{
//        mockMvc.perform(put("/utilisateurs/50")
//                        .content("{\"id_utilisateur\":2,\"nom\":\"}")
//                        .contentType(MediaType.APPLICATION_JSON))       //specifie le type de contenu =json
//                .andExpect(status().isBadRequest());        //badRequest comme dans la methode update de utilisateurController
//    }
//
//    @Test
//    void should_delete_one_utilisateur() throws Exception{
//        mockMvc.perform(delete("/utilisateurs/3")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void should_not_delete_one_utilisateur() throws Exception{
//        mockMvc.perform(delete("/utilisateurs/50")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//}
