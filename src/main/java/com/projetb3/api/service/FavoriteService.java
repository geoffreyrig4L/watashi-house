package com.projetb3.api.service;

import com.projetb3.api.model.Favorite;
import com.projetb3.api.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public Iterable<Favorite> getAll(){
        return favoriteRepository.findAll();
    }

    public Optional<Favorite> favoritesOfUser(int id) {
        return favoriteRepository.favoritesOfUser(id);
    }

    public void save(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public Optional<Favorite> get(int id) {
        return favoriteRepository.findById(id);
    }

    public void deleteItemOfFavorites(int id_item, int id_favorite) {
        favoriteRepository.deleteItemOfFavorites(id_item, id_favorite);
    }

    public void deleteAllItemsOfFavorites(int id) {
        favoriteRepository.deleteAllItemsOfFavorites(id);
    }

    public void addItemOfFavorites(int id_item, int id_favorite) {
        favoriteRepository.addItemInFavorites(id_item, id_favorite);
    }

    public void delete(final int id) {
        favoriteRepository.deleteById(id);
    }

}

