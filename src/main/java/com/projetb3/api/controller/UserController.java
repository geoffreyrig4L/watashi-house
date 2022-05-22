package com.projetb3.api.controller;

import com.projetb3.api.model.User;
import com.projetb3.api.security.AuthenticationWithJWT;
import com.projetb3.api.security.Password;
import com.projetb3.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAll() {
        Iterable<User> usersList = userService.getAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") final int id) { //PathVariable -> permet de manipuler des variables dans l'URI de la requete mapping
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nom={nom}")
    public ResponseEntity<User> getByName(@PathVariable("nom") final String lastname) {
        Optional<User> user = userService.getByName(lastname);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<User> optUser = userService.get(id);
        if (optUser.isPresent()){
            userService.delete(id);
            return ResponseEntity.ok().body("L'utilisateur a été supprimé.");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody User modified) {
        Optional<User> optUser = userService.get(id);
        if (optUser.isPresent()) {
            User current = optUser.get();
            if (modified.getId() != 0) {
                current.setId(modified.getId());
            }
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
            if(modified.getAddress() != null) {
                current.setAddress(modified.getAddress());
            }
            if(modified.getZipCode() != null) {
                current.setZipCode(modified.getZipCode());
            }
            if(modified.getCity() != null){
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
        return ResponseEntity.badRequest().body("L'utilisateur est introuvable.");
    }

    /*@PostMapping("/connexion")
    public ResponseEntity<String> signIn(@RequestBody User userSupplied){
        var userFound = userService.getByEmail(userSupplied.getEmail());
        if(userFound == null || !isHashSame(userSupplied, userFound.getHash())){
            return ResponseEntity.ok("L'email et / ou le mot de passe sont invalides.");
        }
        String jwt = AuthenticationWithJWT.create(userFound);
        return ResponseEntity.ok(jwt);
    }

    private boolean isHashSame(User userSupplied, String hashUserFound) {
        String hash = Password.init().hash(userSupplied.getHash().toCharArray(), userSupplied.getSalt() );
        System.out.println(hash + " - " + hashUserFound);
        return hashUserFound.equals(hash);
    }*/

    @PostMapping("/connexion")
    public ResponseEntity<String> signIn(@RequestBody User userSupplied){
        var userFound = userService.getByEmail(userSupplied.getEmail());
        if(userFound == null){
            return ResponseEntity.ok("L'email et / ou le mot de passe sont invalides.");
        }
        String jwt = AuthenticationWithJWT.create(userFound);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user){
        System.out.println("sign up");
        System.out.println(user.toString());
//        password(user);
//        userService.save(user);
        return ResponseEntity.ok().body("Vous êtes désormais inscrit.");
    }

    public void password(User user) {
        byte[] salt = salt();
        user.setHash(Password.hash(user.getHash().toCharArray(), salt));
        user.setSalt(salt);
        System.out.println(user.getSalt() + " ---------- " + user.getHash());
    }

    private byte[] salt() {
        var random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
