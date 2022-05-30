package com.projetb3.api.service;

import com.projetb3.api.model.Bucket;
import com.projetb3.api.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BucketService {

    @Autowired
    private BucketRepository bucketRepository;

    public Iterable<Bucket> getAll() {           //Optional -> encapsule un objet dont la valeur peut être null
        return bucketRepository.findAll();
    }

}
