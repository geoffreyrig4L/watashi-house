package com.projetb3.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_collection;

    @Column(name = "nom")
    private String nom;

    @OneToMany(
            targetEntity = Article.class,
            mappedBy = "collection",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    List<Article> articles = new ArrayList<>();
}
