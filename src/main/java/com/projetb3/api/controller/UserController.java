package com.projetb3.api.controller;

import com.projetb3.api.model.User;
import com.projetb3.api.security.AuthenticationWithJWT;
import com.projetb3.api.security.Password;
import com.projetb3.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<User> get(@PathVariable("id") final int id) {
        Optional<User> user = userService.get(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nom={nom}")
    public ResponseEntity<Iterable<User>> getByName(@PathVariable("nom") final String lastname) {
        Iterable<User> usersList = userService.getByName(lastname);
        return ResponseEntity.ok(usersList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<User> optUser = userService.get(id);
        if (optUser.isPresent()) {
            userService.delete(id);
            return ResponseEntity.ok().body("L'utilisateur a été supprimé.");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody User modified) {
        Optional<User> optUser = userService.get(id);
        System.out.println(modified.toString());
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
                current.setHash(modified.getHash());
            }
            if (modified.getSalt() != null) {
                current.setSalt(modified.getSalt());
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
        return ResponseEntity.badRequest().body("L'utilisateur est introuvable.");
    }

    /*private boolean isHashSame(User userSupplied, String hashUserFound) {
        String hash = Password.init().hash(userSupplied.getHash().toCharArray(), userSupplied.getSalt() );
        System.out.println(hash + " - " + hashUserFound);
        return hashUserFound.equals(hash);
    }*/

    @PostMapping(path="/connexion", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> signIn(@RequestBody User user) {
        System.out.println(user.getEmail());
        var userFound = userService.getByEmail(user.getEmail());
        if (userFound == null) {
            return ResponseEntity.badRequest().build();
        }
        String jwt = AuthenticationWithJWT.create(userFound);
        Map<String, Object> json = new HashMap<>();
        json.put("token", jwt);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        password(user);
        userService.save(user);
        return ResponseEntity.ok().body("Vous êtes désormais inscrit.");
    }

    public void password(User user) {
        byte[] salt = salt();
        user.hashToString(Password.hash(user.getHash().toCharArray(), salt));
        user.saltToString(salt);
    }

    private byte[] salt() {
        var random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
