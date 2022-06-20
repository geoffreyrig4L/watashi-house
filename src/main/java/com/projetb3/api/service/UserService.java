package com.projetb3.api.service;

import com.projetb3.api.model.User;
import com.projetb3.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return userRepository.findById(id);
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(final int id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public List<User> getByName(String lastname) {
        return userRepository.findAllByName(lastname);
    }

    public void createCartAndFavoritesToUser(int id_user) {
        userRepository.createCartToUser(id_user);
        userRepository.createFavoritesToUser(id_user);
    }
}
