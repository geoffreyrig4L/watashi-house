package com.projetb3.api.repository;

import com.projetb3.api.model.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie,Integer> {

    @Query(value = "select distinct c.* from categories c, pieces_categories pc WHERE c.id_categorie = pc.categorie_id AND pc.piece_id = :piece_id ;", nativeQuery = true)
    List<Categorie> categoriesDUnePiece(@Param("piece_id") int piece_id);
}
