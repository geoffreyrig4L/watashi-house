package com.projetb3.api.service;

import com.projetb3.api.model.Category;
import com.projetb3.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<Category> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return categoryRepository.findById(id);
    }

    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void delete(final int id) {
        categoryRepository.deleteById(id);
    }

    public Category save(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return savedCategory;
    }

    public List<Category> getCategoriesOfRoom(int id_room) {
        return categoryRepository.categoriesOfRoom(id_room);
    }
}
