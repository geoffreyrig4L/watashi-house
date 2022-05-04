package com.projetb3.api.repository;

import com.projetb3.api.model.Opinion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion,Integer> {

}
