package com.projetb3.api.controller;

import com.projetb3.api.model.User;
import com.projetb3.api.security.Password;
import com.projetb3.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.projetb3.api.security.AuthenticationWithJWT.create;
import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/utilisateurs")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAll(@RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<User> usersList = userService.getAll();
            return ResponseEntity.ok(usersList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<User> user = userService.get(id);
        if (user.isPresent() && verifySenderOfRequest(token, Optional.of(user.get().getFirstname()))) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nom={nom}")
    public ResponseEntity<Iterable<User>> getByName(@PathVariable("nom") final String lastname, @RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.empty())) {
            Iterable<User> usersList = userService.getByName(lastname);
            return ResponseEntity.ok(usersList);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token){
        if (verifySenderOfRequest(token, Optional.empty())) {
            Optional<User> optUser = userService.get(id);
            if (optUser.isPresent()) {
                userService.delete(id);
                return ResponseEntity.ok().body("L'utilisateur a été supprimé.");
            }
        }
        return ResponseEntity.badRequest().body("🛑 L'utilisateur n'a pas été supprimé");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody User modified, @RequestHeader("Authentication") final String token) {
        if (verifySenderOfRequest(token, Optional.of(modified.getFirstname()))) {
            Optional<User> optUser = userService.get(id);
            if (optUser.isPresent()) {
                User current = optUser.get();
                if (modified.getGender() != null) {
                    current.setGender(modified.getGender());
                }
                if (modified.getFirstname() != null) {
                    current.setFirstname(modified.getFirstname());
                }
                if (modified.getLastname() != null) {
                    current.setLastname(modified.getLastname());
                }
                if (modified.getEmail() != null) {
                    current.setEmail(modified.getEmail());
                }
                if (modified.getPhone() != null) {
                    current.setPhone(modified.getPhone());
                }
                if (modified.getAddress() != null) {
                    current.setAddress(modified.getAddress());
                }
                if (modified.getZipCode() != null) {
                    current.setZipCode(modified.getZipCode());
                }
                if (modified.getHash() != null) {
                    createHashAndSalt(current, modified.getHash());
                }
                if (modified.getCity() != null) {
                    current.setCity(modified.getCity());
                }
                if (modified.getCountry() != null) {
                    current.setCountry(modified.getCountry());
                }
                if (modified.getTypeUser() != null) {
                    current.setTypeUser(modified.getTypeUser());
                }
                userService.save(current);
                return ResponseEntity.ok().body("L'utilisateur " + current.getId() + " a été modifié.");
            }
            return ResponseEntity.badRequest().body("🛑 L'utilisateur est introuvable.");
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean isHashSame(String hashUserSupplied, User userSaved) {
        return Password.validate(hashUserSupplied.toCharArray(), userSaved.getHash());
    }

    @PostMapping("/connexion")
    public ResponseEntity<Object> signIn(@RequestBody User user) {
        var userSaved = userService.getByEmail(user.getEmail());
        if (userSaved == null || !isHashSame(user.getHash(), userSaved)) {
            return ResponseEntity.badRequest().body("🛑 Cette combinaison e-mail / mot de passe n'existe pas.");
        }
        String jwt = create(userSaved);
        Map<String, Object> json = new HashMap<>();
        json.put("token", jwt);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        createHashAndSalt(user, user.getHash());
        userService.save(user);
        userService.createCartAndFavoritesToUser(user.getId());
        return ResponseEntity.ok().body("Vous êtes désormais inscrit.");
    }

    public void createHashAndSalt(User user, String password) {
        byte[] salt = Password.createSalt();
        String hash = Password.create(password.toCharArray(), salt);
        user.setSalt(salt);
        user.setHash(hash);
    }

    @PostMapping("deconnexion")
    public ResponseEntity<String> logOut(){
        return ResponseEntity.ok().body("Vous êtes désormais déconnectée .");
    }
}
