package com.projetb3.api.controller;

import com.projetb3.api.security.AuthenticationWithJWT;
import com.projetb3.api.model.User;
import com.projetb3.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/session")
public class SessionController {

    UserService userService;

    @PostMapping("/connexion")
    public ResponseEntity<String> signIn(@RequestBody User user){
        boolean isExists = userService.userExists(user.getEmail(), user.getPassword());
        if(!isExists){
            return ResponseEntity.ok("Email et / ou mot de passe invalide");
        }
        String jwt = AuthenticationWithJWT.create();
        return ResponseEntity.status(200).body(jwt);
    }

//    @PostMapping("/inscription")
//    public ResponseEntity<String> signUp(@RequestBody User user){
//        userService.save(user);
//        ResponseEntity.ok("Vous êtes désormais inscris à notre site.");
//    }
}
