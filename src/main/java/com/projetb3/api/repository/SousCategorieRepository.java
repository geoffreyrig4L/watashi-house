package com.projetb3.api.repository;

import com.projetb3.api.model.SousCategorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SousCategorieRepository extends CrudRepository<SousCategorie,Integer> {
}
