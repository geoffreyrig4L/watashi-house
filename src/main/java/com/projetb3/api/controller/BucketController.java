package com.projetb3.api.controller;

import com.projetb3.api.model.Bucket;
import com.projetb3.api.service.BucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paniers")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Bucket>> getAll() {
        Iterable<Bucket> listeCategorys = bucketService.getAll();
        return ResponseEntity.ok(listeCategorys);
    }
}
