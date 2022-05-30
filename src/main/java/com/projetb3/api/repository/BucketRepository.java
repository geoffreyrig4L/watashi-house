package com.projetb3.api.repository;

import com.projetb3.api.model.Bucket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends CrudRepository<Bucket,Integer> {
}
