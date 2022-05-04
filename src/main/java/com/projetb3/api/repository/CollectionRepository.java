package com.projetb3.api.repository;

import com.projetb3.api.model.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends CrudRepository<Collection,Integer> {

}
