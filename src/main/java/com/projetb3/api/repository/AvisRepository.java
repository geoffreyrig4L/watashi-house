package com.projetb3.api.repository;

import com.projetb3.api.model.Avis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisRepository extends CrudRepository<Avis,Integer> {
}
