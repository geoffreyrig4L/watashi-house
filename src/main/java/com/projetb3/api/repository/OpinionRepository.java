package com.projetb3.api.repository;

import com.projetb3.api.model.Item;
import com.projetb3.api.model.Opinion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion,Integer> {

    @Query(value = "SELECT DISTINCT a.*, u.prenom FROM avis a, utilisateurs u WHERE a.utilisateur_id = u.id_utilisateur", nativeQuery = true)
    List<Opinion> findAllWithNameOfAuthor();

    @Query(value = "SELECT DISTINCT * FROM avis a WHERE article_id = :id_article", nativeQuery = true)
    List<Opinion> opinionsOfItem(@Param("id_article") int id);

    @Query(value = "SELECT DISTINCT AVG(note) FROM avis WHERE article_id = :id_article", nativeQuery = true)
    Float averageNote(@Param("id_article") int id);
}
