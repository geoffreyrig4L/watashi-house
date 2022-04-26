package com.projetb3.api.repository.jpa;

import com.projetb3.api.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieJPARepository extends JpaRepository<Categorie, Integer> {

    //List<Categorie> findCategoriesByid_article(int id_article);
}
