package com.projetb3.api.service;

import com.projetb3.api.model.SubCategory;
import com.projetb3.api.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public Optional<SubCategory> get(final int id) {           //Optional -> encapsule un objet dont la valeur peut être null
        return subCategoryRepository.findById(id);
    }

    public Iterable<SubCategory> getAll() {
        return subCategoryRepository.findAll();
    }

    public void delete(final int id) {
        subCategoryRepository.deleteById(id);
    }

    public SubCategory save(SubCategory subCategory) {
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return savedSubCategory;
    }

    public List<SubCategory> getSubCategoriesOfCategory(int id_category) {
        return subCategoryRepository.subCategoriesOfCategory(id_category);
    }

    public List<SubCategory> getSubCategoriesOfRoom(int id_room) {
        return subCategoryRepository.subCategoriesOfRoom(id_room);
    }
}
