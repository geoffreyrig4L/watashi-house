package com.projetb3.api.repository;

import com.projetb3.api.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {

    @Query(value = "select distinct c.* from categories c, pieces_categories pc WHERE c.id_categorie = pc.categorie_id AND pc.piece_id = :piece_id ;", nativeQuery = true)
    List<Category> categoriesOfRoom(@Param("piece_id") int piece_id);
}
