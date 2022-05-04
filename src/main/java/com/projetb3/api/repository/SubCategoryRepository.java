package com.projetb3.api.repository;

import com.projetb3.api.model.SubCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory,Integer> {

    @Query(value = "select distinct sc.* from categories c, souscategories sc WHERE sc.categorie_id = :categorie_id ;", nativeQuery = true)
    List<SubCategory> subCategoriesOfCategory(@Param("categorie_id") int categorie_id);

    @Query(value = "select distinct sc.* from souscategories sc, pieces_souscategories psc WHERE sc.id_souscategorie = psc.souscategorie_id AND psc.piece_id = :piece_id ;", nativeQuery = true)
    List<SubCategory> subCategoriesOfRoom(@Param("piece_id") int piece_id);
}
