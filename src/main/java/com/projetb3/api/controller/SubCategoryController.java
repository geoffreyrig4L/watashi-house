package com.projetb3.api.controller;

import com.projetb3.api.model.SubCategory;
import com.projetb3.api.security.AuthenticationWithJWT;
import com.projetb3.api.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.projetb3.api.security.AuthenticationWithJWT.verifySenderOfRequest;

@Controller
@RequestMapping("/sous-categories")
public class SubCategoryController {

    private SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<SubCategory>> getAll() {
        Iterable<SubCategory> subCategoriesList = subCategoryService.getAll();
        return ResponseEntity.ok(subCategoriesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> get(@PathVariable("id") final int id) {
        Optional<SubCategory> subCategory = subCategoryService.get(id);
        if (subCategory.isPresent()) {
            return ResponseEntity.ok(subCategory.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/piece={id_piece}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesOfRoom(@PathVariable("id_piece") final int id_room) {
        List<SubCategory> subCategoriesList = subCategoryService.getSubCategoriesOfRoom(id_room);
        return ResponseEntity.ok(subCategoriesList);
    }

    @GetMapping("/categorie={id_categorie}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesOfCategory(@PathVariable("id_categorie") final int id_category) {
        List<SubCategory> subCategoriesList = subCategoryService.getSubCategoriesOfCategory(id_category);
        return ResponseEntity.ok(subCategoriesList);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SubCategory subCategory, @RequestHeader("Authentication") final String token) {
        if(verifySenderOfRequest(token, Optional.empty())){
            subCategoryService.save(subCategory);
            return ResponseEntity.ok().body("La sous-catégorie a été créée.");
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final int id, @RequestHeader("Authentication") final String token) {
        Optional<SubCategory> optSubCategory = subCategoryService.get(id);
        if (optSubCategory.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            subCategoryService.delete(id);
            return ResponseEntity.ok().body("La sous-catégorie a été supprimée.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSubCategory(@PathVariable("id") final int id,
                                                    @RequestBody SubCategory modified,
                                                    @RequestHeader("Authentication") final String token) {
        Optional<SubCategory> optSubCategory = subCategoryService.get(id);
        if (optSubCategory.isPresent() && verifySenderOfRequest(token, Optional.empty())) {
            SubCategory current = optSubCategory.get();
            if (modified.getName() != null) {
                current.setName(modified.getName());
            }
            subCategoryService.save(current);
            return ResponseEntity.ok().body("La sous-catégorie " + current.getId() + " a été modifiée.");
        }
        return ResponseEntity.notFound().build();
    }

}

