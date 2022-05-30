package com.projetb3.api.controller;

import com.projetb3.api.model.Category;
import com.projetb3.api.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Category>> getAll() {
        Iterable<Category> categoriesList = categoryService.getAll();
        return ResponseEntity.ok(categoriesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable("id") final int id) {
        Optional<Category> category = categoryService.get(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseEntity.ok().body("La catégorie a été créée.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id) {
        Optional<Category> optCategory = categoryService.get(id);
        if (optCategory.isPresent()) {
            categoryService.delete(id);
            return ResponseEntity.ok().body("La catégorie a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final int id, @RequestBody Category modified) {
        Optional<Category> optCategory = categoryService.get(id);
        if (optCategory.isPresent()) {
            Category current = optCategory.get();
            if (modified.getName() != null) {
                current.setName(modified.getName());
            }
            categoryService.save(current);
            return ResponseEntity.ok().body("La catégorie " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/piece={id_room}")
    public ResponseEntity<List<Category>> getCategoriesOfRoom(@PathVariable("id_room") final int id_room) {
        List<Category> listCategories = categoryService.getCategoriesOfRoom(id_room);
        return ResponseEntity.ok(listCategories);
    }

}
