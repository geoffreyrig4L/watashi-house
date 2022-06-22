package com.projetb3.api.repository;

import com.projetb3.api.model.Opinion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion,Integer> {

    @Query(value = "SELECT DISTINCT a.*, u.nom, u.prenom FROM avis a, utilisateurs u WHERE article_id = :id_article and a.utilisateur_id = u.id_utilisateur", nativeQuery = true)
    List<Opinion> opinionsOfItem(@Param("id_article") int id);

    @Query(value = "SELECT AVG(note) FROM avis WHERE article_id = :id_article", nativeQuery = true)
    Float averageNote(@Param("id_article") int id_article);

    @Query(value = "UPDATE articles SET note = :avg WHERE id_article = :id_item", nativeQuery = true)
    @Modifying
    @Transactional
    void setNoteOfItem(@Param("id_item") int id, Float avg);
}